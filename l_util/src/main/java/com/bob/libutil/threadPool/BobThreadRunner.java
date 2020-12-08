package com.bob.libutil.threadPool;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;


public class BobThreadRunner implements Callback, IThreadRunner {
    private final static String LOG_TAG = "TaskRunner";

    //group 管理信息
    private Map<String, GroupInfo> mGroupConcurrents = new HashMap<String, GroupInfo>();
    //所有的任务索引
    private Map<Callable<?>, Task> mAllTasks = new HashMap<Callable<?>, Task>();
    //等待执行的任务优先级队列
    private Queue<Task> mAllWaitingTasks = new PriorityQueue<Task>(10, new TaskComparator());

    private static BobThreadRunner sTaskHolder;
    //线程池
    private ScheduledExecutorService mThreadPool;

    private Handler mMainHandler;

    private final int DEFAULT_MAX_THREADNUM = 4;


    protected BobThreadRunner() {
        int coreNum = getNumCores();
        if (coreNum == 0)
            coreNum = 2;
        mThreadPool = Executors.newScheduledThreadPool(coreNum * DEFAULT_MAX_THREADNUM, new ThreadFactory() {
            private AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable paramRunnable) {
                Thread thread = new Thread(paramRunnable);
                thread.setName("ALMThread-" + mCount.getAndIncrement());
                return thread;
            }

        });
        mMainHandler = new Handler(Looper.getMainLooper(), this);
    }

    public static synchronized BobThreadRunner getInstance() {
        if (sTaskHolder == null)
            sTaskHolder = new BobThreadRunner();
        return sTaskHolder;
    }

    private int getNumCores() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        int size = 2;

        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());

            if (files != null && files.length > 0) {

                size = files.length;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }

    public boolean isTaskCancelled(Callable<?> callable) {
        boolean ret = false;

        Task task = mAllTasks.get(callable);
        if (null != task) {
            ret = task.isCancelled;
        }

        return ret;
    }

    /**
     * 执行任务
     *
     * @param callable callable，异步执行代码
     * @param callback 执行结果回调，主线程调用
     * @param group    任务分组
     */
    public synchronized void runTask(Callable<?> callable, Callback callback, String group, BobThreadPriority priority) {
        if (callable == null)
            return;

        //生成task
        Task task = new Task();
        task.mCallable = new CallableWrapper(callable);
        task.mCallback = callback;
        task.mGroupName = group;
        task.mPriority = priority;
        task.isCancelled = false;

        if (TextUtils.isEmpty(group)) {
            //无分组，直接执行
            task.mFuture = mThreadPool.submit(task.mCallable);
        } else {
            GroupInfo groupInfo = mGroupConcurrents.get(group);
            if (groupInfo == null) {
                //未存在分组，建立分组
                groupInfo = new GroupInfo();
                mGroupConcurrents.put(task.mGroupName, groupInfo);
            }
            if (groupInfo.mRunningTasks.size() < groupInfo.mConcurrents && !groupInfo.mPaused) {
                task.mFuture = mThreadPool.submit(task.mCallable);
                groupInfo.mRunningTasks.add(task);
            } else {
                groupInfo.mWaitingTasks.add(task);
                mAllWaitingTasks.add(task);
            }
        }

        mAllTasks.put(callable, task);
    }

    /**
     * 取消任务
     *
     * @param callable callable，异步执行代码
     * @param force    是否强制结束，强制：终止正在运行的任务
     */
    public synchronized void cancelTask(Callable<?> callable, boolean force) {
        Task task = mAllTasks.get(callable);
        if (task == null)
            return;

        task.isCancelled = true;

        if (null != task.mFuture) {
            if (!task.mFuture.cancel(force)) {
                return;
            }
        }

        // remove from the group related queue
        if (!TextUtils.isEmpty(task.mGroupName)) {
            GroupInfo groupInfo = mGroupConcurrents.get(task.mGroupName);
            if (groupInfo != null) {
                if (task.mFuture != null) {
                    groupInfo.mRunningTasks.remove(task);
                } else {
                    groupInfo.mWaitingTasks.remove(task);
                    mAllWaitingTasks.remove(task);
                }
            }
        }

        //移除记录
        mAllTasks.remove(callable);
    }

    public Future<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return mThreadPool.schedule(command, delay, unit);
    }

    public <V> Future<V> schedule(Callable<V> callable, long delay,
                                  TimeUnit unit) {
        return mThreadPool.schedule(callable, delay, unit);
    }

    public Future<?> scheduleAtFixedRate(Runnable command, long initialDelay,
                                         long period, TimeUnit unit) {
        return mThreadPool.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public Future<?> scheduleWithFixedDelay(Runnable command,
                                            long initialDelay, long delay, TimeUnit unit) {
        return mThreadPool.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    public synchronized void cancelGroup(String groupName, boolean force) {
        //分组空，则设置无效
        if (TextUtils.isEmpty(groupName))
            return;
        GroupInfo groupInfo = mGroupConcurrents.get(groupName);
        if (groupInfo == null)
            return;

        //cancel 待执行任务
        while (groupInfo.mWaitingTasks.size() > 0) {
            Task task = groupInfo.mWaitingTasks.poll();
            task.isCancelled = true;
            mAllTasks.remove(task.mCallable.mRealCallable);
            mAllWaitingTasks.remove(task);
        }

        //cancel 正在执行的任务
        while (groupInfo.mRunningTasks.size() > 0) {
            Task task = groupInfo.mRunningTasks.get(groupInfo.mRunningTasks.size() - 1);
            task.isCancelled = true;

            if (force) {
                //强制停止
                if (task.mFuture != null)
                    task.mFuture.cancel(true);
            } else {
                //非强制执行
                if (task.mFuture != null) {
                    //正在执行任务，则试图结束任务
                    if (!task.mFuture.cancel(false))
                        continue;
                }
            }
            //移除记录
            mAllTasks.remove(task.mCallable.mRealCallable);
            groupInfo.mRunningTasks.remove(groupInfo.mRunningTasks.size() - 1);

        }

        mGroupConcurrents.remove(groupName);
    }

    public synchronized void pauseGroup(String groupName) {
        //分组空，则设置无效
        if (TextUtils.isEmpty(groupName))
            return;
        GroupInfo groupInfo = mGroupConcurrents.get(groupName);
        if (groupInfo != null) {
            //已经存在分组
            groupInfo.mPaused = true;
        } else {
            //未存在分组，建立分组
            groupInfo = new GroupInfo();
            groupInfo.mPaused = true;
            mGroupConcurrents.put(groupName, groupInfo);
        }
    }

    public synchronized void resumeGroup(String groupName) {
        //分组空，则设置无效
        if (TextUtils.isEmpty(groupName))
            return;
        GroupInfo groupInfo = mGroupConcurrents.get(groupName);
        if (groupInfo == null)
            return;
        //已经存在分组
        groupInfo.mPaused = false;
        do {
            if (groupInfo.mRunningTasks.size() < groupInfo.mConcurrents && groupInfo.mWaitingTasks.size() > 0) {
                Task task = groupInfo.mWaitingTasks.poll();
                task.mFuture = mThreadPool.submit(task.mCallable);
                groupInfo.mRunningTasks.add(task);
                mAllWaitingTasks.remove(task);
            } else {
                break;
            }
        } while (true);

    }

    /**
     * 设置分组的任务并发数
     *
     * @param groupName   组名
     * @param concurrents 允许该组并发的任务数
     */
    public synchronized void setGroupConcurrents(String groupName, int concurrents) {
        //分组空，则设置无效
        if (TextUtils.isEmpty(groupName))
            return;
        GroupInfo groupInfo = mGroupConcurrents.get(groupName);
        if (groupInfo != null) {
            //已经存在分组
            groupInfo.mConcurrents = concurrents;
        } else {
            //未存在分组，建立分组
            groupInfo = new GroupInfo();
            groupInfo.mConcurrents = concurrents;
            mGroupConcurrents.put(groupName, groupInfo);
        }
    }


    class GroupInfo {
        public int mConcurrents = Integer.MAX_VALUE;
        public boolean mPaused = false;
        public Queue<Task> mWaitingTasks = new PriorityQueue<Task>(5, new TaskComparator());
        public List<Task> mRunningTasks = new ArrayList<Task>();
    }


    class TaskComparator implements Comparator<Task> {
        @Override
        public int compare(Task paramT1, Task paramT2) {
            if (paramT1.mPriority == null && paramT2.mPriority == null)
                return 0;
            else if (paramT1.mPriority == null)
                return -1;
            else if (paramT2.mPriority == null)
                return 1;
            return paramT1.mPriority.compareTo(paramT2.mPriority) * -1; //优先级高得排在前面
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        synchronized (this) {
            CallableWrapper callableWrapper = (CallableWrapper) msg.obj;
            Task task = mAllTasks.get(callableWrapper.mRealCallable);
            if (task != null) {

                //call back
                if (!task.isCancelled) {
                    Callback callback = task.mCallback;
                    if (callback != null) {
                        Handler mainThreadHandler = new Handler(Looper.getMainLooper(), callback);
                        Message message = Message.obtain();
                        try {
                            Object ret = task.mFuture.get();
                            message.obj = ret;
                            if (ret instanceof Throwable)
                                message.obj = null;
                        } catch (InterruptedException e) {
                            Log.w(LOG_TAG, "--->>future get result failed:" + e.getMessage());
                        } catch (ExecutionException e) {
                            Log.w(LOG_TAG, "--->>future get result failed:" + e.getMessage());
                        }
                        mainThreadHandler.sendMessage(message);
                    }
                }

                //执行完成，移除记录
                mAllTasks.remove(callableWrapper.mRealCallable);

                if (!TextUtils.isEmpty(task.mGroupName)) {
                    GroupInfo groupinfo = mGroupConcurrents.get(task.mGroupName);
                    if (groupinfo != null) {
                        groupinfo.mRunningTasks.remove(task);
                    }
                }

            } else
                Log.i(LOG_TAG, "--->>An removed task has finished" + (null != task ? (" is cancelled? " + task.isCancelled) : ""));
            //根据优先级获取需要执行的任务


            Iterator<Task> interator = mAllWaitingTasks.iterator();
            while (interator.hasNext()) {

                Task toRun = interator.next();

                if (TextUtils.isEmpty(toRun.mGroupName)) {
                    //没有group的被挂起执行啦
                    Log.i(LOG_TAG, "--->>an task dose not belong to any group has not been excute Immediately");
                    interator.remove();
                    toRun.mFuture = mThreadPool.submit(toRun.mCallable);
                    mAllTasks.put(toRun.mCallable.mRealCallable, toRun);
                    break;
                } else {
                    GroupInfo groupinfo = mGroupConcurrents.get(toRun.mGroupName);
                    if (groupinfo == null) {
                        groupinfo = new GroupInfo();
                        Log.i(LOG_TAG, "first task belongs to a group has not been excute Immediately");
                        mGroupConcurrents.put(toRun.mGroupName, groupinfo);
                    }
                    if (groupinfo.mRunningTasks.size() < groupinfo.mConcurrents && !groupinfo.mPaused) {
                        toRun.mFuture = mThreadPool.submit(toRun.mCallable);
                        interator.remove();
                        groupinfo.mWaitingTasks.remove(toRun);
                        groupinfo.mRunningTasks.add(toRun);
                        continue;
                    } else
                        break;

                }

            }
        }
        return true;
    }

    class CallableWrapper implements Callable<Object> {
        private Callable<?> mRealCallable;

        public CallableWrapper(Callable<?> callable) {
            mRealCallable = callable;
        }

        @Override
        public Object call() throws Exception {
            if (mRealCallable == null)
                return null;

            Object ret = null;
            try {
                ret = mRealCallable.call();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            Message msg = Message.obtain();
            msg.obj = this;
            mMainHandler.sendMessage(msg);


            return ret;
        }
    }

    private static class Task {
        private CallableWrapper mCallable;
        private Callback mCallback;
        private String mGroupName;
        private Future<?> mFuture;
        private BobThreadPriority mPriority;
        private boolean isCancelled;
    }

}
