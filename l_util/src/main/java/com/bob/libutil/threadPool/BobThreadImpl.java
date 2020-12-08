package com.bob.libutil.threadPool;

import android.os.Handler.Callback;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class BobThreadImpl implements BobThread {

    private boolean mStarted = false;
    private BobThreadPriority mPriority = BobThreadPriority.NORMAL;
    private String mGroupName = null;
    private Callable<?> mCallable;

    protected BobThreadImpl() {

    }

    @Override
    public void start(Runnable task) {

        if (!mStarted) {
            mCallable = Executors.callable(task);
            BobRunnerController.getThreadRunner().runTask(mCallable, null, mGroupName, mPriority);
            mStarted = true;
        }
    }

    @Override
    public void start(Callable<?> task, Callback callback) {
        if (!mStarted) {
            mCallable = task;
            BobRunnerController.getThreadRunner().runTask(task, callback, mGroupName, mPriority);
            mStarted = true;
        }
    }

    @Override
    public void cancel(boolean mayInterruptIfRunning) {
        BobRunnerController.getThreadRunner().cancelTask(mCallable, mayInterruptIfRunning);
    }

    @Override
    public void setPriority(BobThreadPriority priority) {
        mPriority = priority;
    }

    @Override
    public BobThread addThread2Group(String groupName) {
        if (!mStarted)
            mGroupName = groupName;
        return this;
    }

    @Override
    public void removeThreadFromGroup(String groupName) {
        if (!mStarted)
            mGroupName = null;
    }

    @Override
    public void cancelGroupThread(boolean force) {
        BobRunnerController.getThreadRunner().cancelGroup(mGroupName, force);
    }

    @Override
    public void pauseGroupThread() {
        BobRunnerController.getThreadRunner().pauseGroup(mGroupName);
    }

    @Override
    public void resumeGroupThread() {
        BobRunnerController.getThreadRunner().resumeGroup(mGroupName);
    }

    @Override
    public void setGroupConcurrents(int concurrents) {
        BobRunnerController.getThreadRunner().setGroupConcurrents(mGroupName, concurrents);
    }


    @Override
    public boolean isCancelled() {
        return BobRunnerController.getThreadRunner().isTaskCancelled(mCallable);
    }

    @Override
    public String getGroupName() {
        return mGroupName;
    }

    @Override
    public BobThreadPriority getPriority() {
        return mPriority;
    }
}
