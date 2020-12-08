package com.bob.libutil.threadPool;

import android.os.Handler.Callback;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的具体运行器，允许第三方开发者将自己的线程池注入到当前应用线程池中，用于保证其他sdk的运行的线程的数量处理不会侵入到app本身的线程池的数量
 */
public interface IThreadRunner {

    /**
     * 执行线程任务的处理
     *
     * @param callable
     * @param callback
     * @param group
     * @param priority
     */
    public void runTask(Callable<?> callable, Callback callback, String group, BobThreadPriority priority);

    public boolean isTaskCancelled(Callable<?> callable);

    /**
     * 取消任务的执行
     *
     * @param callable
     * @param force
     */
    public void cancelTask(Callable<?> callable, boolean force);

    public Future<?> schedule(Runnable command, long delay, TimeUnit unit);

    public <V> Future<V> schedule(Callable<V> callable, long delay, TimeUnit unit);

    public Future<?> scheduleAtFixedRate(Runnable command, long initialDelay,
                                         long period, TimeUnit unit);

    public Future<?> scheduleWithFixedDelay(Runnable command,
                                            long initialDelay, long delay, TimeUnit unit);


    public void cancelGroup(String groupName, boolean force);

    public void pauseGroup(String groupName);

    public void resumeGroup(String groupName);

    public void setGroupConcurrents(String groupName, int concurrents);
}
