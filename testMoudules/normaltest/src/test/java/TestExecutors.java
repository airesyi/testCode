import org.junit.Test;

import java.util.concurrent.*;

public class TestExecutors {
    @Test
    public void test1() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(index);
                }
            });
        }
    }

    @Test
    public void test2() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Test
    public void test3() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.schedule(new Runnable() {
            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);
    }

    @Test
    public void test4() throws InterruptedException {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        final CountDownLatch latch = new CountDownLatch(1);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("delay 1 seconds, and excute every 3 seconds");
                latch.countDown();
            }
        }, 1, 3, TimeUnit.SECONDS);
        latch.await();
    }

    @Test
    public void test5() throws InterruptedException {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        final CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                public void run() {
                    System.out.println(index);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
}
