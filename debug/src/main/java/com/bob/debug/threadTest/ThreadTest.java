package com.bob.debug.threadTest;

import com.elvishew.xlog.XLog;

/**
 * created by cly on 2020/12/21
 */
public class ThreadTest {

    public static synchronized void test1() {
        XLog.d("@cly_ test1 - start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        XLog.d("@cly_ test1 - end");
    }

    public void test2() {
        synchronized (ThreadTest.this) {
            XLog.d("@cly_ test2 - start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            XLog.d("@cly_ test2 - end");
        }
    }
}
