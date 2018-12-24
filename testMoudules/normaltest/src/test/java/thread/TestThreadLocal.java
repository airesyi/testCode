package thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * auth: shi yi
 * create date: 2018/12/14
 */
public class TestThreadLocal {
    public static int a = 1;
    public static Map<String, ReentrantLock> cus = new HashMap<>();
    private static int num = 200;

    public static CountDownLatch countDownLatch;

    public static int newadd = 0;
    public static int getadd = 0;

    public static void main(String[] args) {
        /*thread.Test1 test1 = new thread.Test1();

        for (int i = 0; i < 50; i++) {
            Thread thread1 = new Thread(test1);
            thread1.start();
        }*/
        countDownLatch = new CountDownLatch(num);
        RunMethod runMethod = new RunMethod();
        for (Integer i = 0; i < num; i++) {
            Integer j = i % 20;

            Test2 test2 = new Test2(runMethod, j.toString());
            Thread thread = new Thread(test2);
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("newadd:" + newadd + "    " + "getadd:" + getadd);
    }

}

class RunMethod {
    public String executeMethod(String str) {
        /*ReentrantLock reentrantLock = null;
        synchronized (this){}
            if (thread.TestThreadLocal.cus.get(str) == null) {
                reentrantLock = new ReentrantLock();
                thread.TestThreadLocal.cus.put(str, reentrantLock);
                System.out.println("newadd:" + thread.TestThreadLocal.newadd++);
            } else {
                reentrantLock = thread.TestThreadLocal.cus.get(str);
                System.out.println("getadd:" + thread.TestThreadLocal.getadd++);
            }

        try {
            reentrantLock.lock();
            Thread.sleep(500);
            System.out.println(Thread.currentThread() + ":" + str);
            thread.TestThreadLocal.cus.remove(str);
            reentrantLock.unlock();
            reentrantLock = null;
        } catch (InterruptedException e) {
            System.out.println("error");
            e.printStackTrace();
        }*/

        try {
            MyReentrantLock myReentrantLock = new MyReentrantLock(str);
            myReentrantLock.lock();
            System.out.println(Thread.currentThread() + ":" + str);
            Thread.sleep(100);
            myReentrantLock.unlock();
            TestThreadLocal.countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*ReentrantLock reentrantLock = thread.ReentrantLockFactory.getReentrantLock(str);
        reentrantLock.lock();
        System.out.println(Thread.currentThread() + ":" + str);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reentrantLock.unlock();
*/
        return str;
    }
}

class Test2 implements Runnable {
    private RunMethod runMethod;
    private String string;

    public Test2(RunMethod runMethod, String string) {
        this.runMethod = runMethod;
        this.string = string;
    }

    @Override
    public void run() {
        runMethod.executeMethod(string);
    }
}

class ReentrantLockFactory {
    private static Map<String, ReentrantLock> cus = new HashMap<>();

    public static ReentrantLock getReentrantLock(String string) {
        ReentrantLock reentrantLock;
        synchronized (cus) {
            if (cus.get(string) == null) {
                reentrantLock = new ReentrantLock();
                cus.put(string, reentrantLock);
            } else {
                reentrantLock = cus.get(string);
            }
        }
        return reentrantLock;
    }
}


class MyReentrantLock {
    private static Map<String, ReentrantLock> cus = new HashMap<>();
    private ReentrantLock reentrantLock = null;
    private String string;

    public MyReentrantLock(String string) {
        this.string = string;
        this.reentrantLock = getReentrantLock(string);
    }

    private static ReentrantLock getReentrantLock(String string) {
        ReentrantLock reentrantLock;
        synchronized (cus) {
        }
        if (cus.get(string) == null) {
            reentrantLock = new ReentrantLock();
            cus.put(string, reentrantLock);
            TestThreadLocal.newadd++;
        } else {
            reentrantLock = cus.get(string);
            TestThreadLocal.getadd++;
        }

        return reentrantLock;
    }

    public void lock() {
        this.reentrantLock.lock();
    }

    public void unlock() {
        this.reentrantLock.unlock();
        if (reentrantLock.isLocked()) {
            System.out.println("立刻被使用");
        } else {
            cus.remove(string);
        }
        this.reentrantLock = null;
    }
}

class Test1 implements Runnable {

    ReentrantLock reentrantLock = new ReentrantLock();


    @Override
    public void run() {
        int a = TestThreadLocal.a++;
        if (a % 2 == 0) {
            reentrantLock.lock();
        }
        System.out.println("this is : " + Thread.currentThread() + a);
        if (a % 2 == 1) {
            reentrantLock.unlock();
        }

        if (a % 2 == 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reentrantLock.unlock();
        }

    }
}