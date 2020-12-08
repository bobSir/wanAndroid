package com.bob.libutil.threadPool;

/**
 * 线程运行器的调度，对于使用当前线程进行线程调度的sdk来说，第三方开发者在接入sdk的时候可以通过注入app本身的线程池管理机制来避免sdk的线程池侵入到app的线程池
 */
public class BobRunnerController {

    /**
     * 外部注入的线程池的运行器
     */
    private static IThreadRunner sInjectionRunner;

    /**
     * 允许线程池的使用者注入自己的线程池管理机制
     *
     * @param runner
     */
    public static void setThreadRunner(IThreadRunner runner) {
        sInjectionRunner = runner;
    }

    public static IThreadRunner getThreadRunner() {
        if (sInjectionRunner == null) {
            return BobThreadRunner.getInstance();
        }
        return sInjectionRunner;
    }

}