package com.bob.libutil.threadPool;

import android.os.Handler.Callback;

import java.util.concurrent.Callable;

/**
 * 异步处理线程，用于耗时的操作。一个Thread代表一个任务。应用通过{@link #start(Runnable) start(Runnable)}接口传入task执行。调用start后Thread将不再接受其他操作。
 * Thread支持分步任务处理，应用通过{@link #start(Callable, Callback) start(Callable, Callback)}接口传入Callable和Callback接口。
 * Thread将在执行完Callable任务后将结果通过Message方式传入Callback回调。Callable结果通过Message的obj参数传递。回调在主线程执行。<br>
 * <br>
 * 考虑CPU使用率的控制，Thread管理控制Thread的执行时机，用户调用start后并非立即执行。<br>
 * Thread管理支持分组功能，应用可将Thread并入特定分组，统一管理。<br>
 */
public interface BobThread {
    /**
     * 执行任务<br>
     *
     * @param task 异步执行的Runnable
     */
    public void start(Runnable task);

    /**
     * 执行分步任务，Callable通过异步线程执行，执行完成后将结果通过主线程回调Callback接口。运行结果通过Message的obj参数传递,用途类似android AsyncTask
     * callable接口类似AsyncTask doInBackground方法，Callback接口类似AsyncTask onPostExecute方法<br>
     *
     * @param task     异步执行的Callable
     * @param callback 异步回调接口callback，接口在主线程完成回调。设置为空将无法接受对调事件。
     */
    public void start(Callable<?> task, Callback callback);

    /**
     * 取消任务<br>
     *
     * @param mayInterruptIfRunning 是否结束正在运行的任务。调用此函数后，将收不到回调信息
     */
    public void cancel(boolean mayInterruptIfRunning);

    /**
     * 判断这个线程是否已经取消
     *
     * @return
     */
    public boolean isCancelled();

    /**
     * 设置任务优先级<br>
     *
     * @param priority 任务优先级
     */
    public void setPriority(BobThreadPriority priority);

    /**
     * 获取priority
     */
    public BobThreadPriority getPriority();

    /**
     * 将Thread放入Group，形成Group管理。<br>
     *
     * @param groupName group名
     */
    public BobThread addThread2Group(String groupName);

    /**
     * 将group从组内移除，组操作将影响该Thread<br>
     *
     * @param groupName group名
     */
    public void removeThreadFromGroup(String groupName);

    /**
     * 获取所属的组名，如果不属于任何一个组，则返回null
     */
    public String getGroupName();

    /**
     * 取消该请求group内所有任务，如果该Thread不属于任何任务组，则调用该方法将无效<br>
     *
     * @param force 是否保留正在处理的任务。ture则强行关闭组内所有数据任务，false当组内请求已经发出则保留任务。
     */
    public void cancelGroupThread(boolean force);

    /**
     * 暂停该组内未处理的所有任务，如果该Thread不属于任何任务组，则调用该方法将无效。<br>
     */
    public void pauseGroupThread();

    /**
     * 启动该线程的线程组的所有任务，如果该Thread不属于任何任务组，则调用该方法将无效<br>
     */
    public void resumeGroupThread();

    /**
     * 设置任务组并发任务数。当该组内任务数到达设置值时，后续加入任务将进入队列等待。如果该Thread不属于任何任务组，则调用该方法将无效。<br>
     *
     * @param concurrents 组内任务并发数
     */
    public void setGroupConcurrents(int concurrents);
}
