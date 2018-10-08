package sync;

import helper.AbCaller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AbCaller caller = new AbCaller();

        FutureTask<String> task = new FutureTask<String>(caller);
        long startTime = System.currentTimeMillis();
        System.out.println("a:" + Thread.currentThread().getId());
        new Thread(task).start();
        if (!task.isDone()) {  // 联系快递员，询问是否到货
            System.out.println("第三步：厨具还没到，心情好就等着（心情不好就调用cancel方法取消订单）");
        }
        System.out.println("b:" + Thread.currentThread().isAlive());
        Object obj = task.get();
        System.out.println("c:" + Thread.currentThread().getId());

        System.out.println(obj.getClass());
    }
}
