package thread;

/**
 * auth: shi yi
 * create date: 2018/12/21
 */
public class TestThread {
    public static Integer a = 0;
    public static Integer b = 0;

    public static void main(String[] args) {
        Count count = new Count();

        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(count);
            thread.start();
        }
    }

}

class Count implements Runnable {
    @Override
    public void run() {
        synchronized (TestThread.b) {

            try {
                System.out.println(TestThread.a);
                Thread.sleep(50);
                System.out.println(TestThread.a++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
