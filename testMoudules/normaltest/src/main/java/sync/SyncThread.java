package sync;

import javax.xml.ws.soap.MTOM;

public class SyncThread implements Runnable {
    private static int count;

    public SyncThread() {
        count = 0;
    }

    @Override
    public void run() {
        synchronized (MTOM.class) {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getCount() {
        return count;
    }
}
