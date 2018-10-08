package helper;

import java.util.concurrent.Callable;

public class AbCaller implements Callable {
    public Object call() throws Exception {
        System.out.println("call:" + Thread.currentThread().getId());
        Thread.sleep(3000);
        System.out.println("call:" + Thread.currentThread().getId());
        return this;
    }
}
