package com.bob.libutil.threadPool;


public class BobThreadFactory {

    private static class InnerThreadImpl extends BobThreadImpl {

        public InnerThreadImpl() {
            super();
        }

        public InnerThreadImpl(BobThreadPriority priority) {
            super();
            this.setPriority(priority);
        }
    }

    /**
     * 获取一个默认优先级的线程处理
     *
     * @return
     */
    public static BobThread newThread() {
        BobThreadImpl thread = new InnerThreadImpl();
        return thread;
    }

    /**
     * 获取一个特定优先级的线程处理
     *
     * @return
     */
    public static BobThread newThread(BobThreadPriority priority) {
        BobThread thread = new InnerThreadImpl(priority);
        return thread;
    }

}
