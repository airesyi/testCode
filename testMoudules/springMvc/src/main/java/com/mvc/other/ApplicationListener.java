package com.mvc.other;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 100, 50000L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5000));
        sce.getServletContext().setAttribute("executor",
                executor);
        System.out.println("监听启动啦");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) sce
                .getServletContext().getAttribute("executor");
        executor.shutdown();
        System.out.println("程序结束啦");
    }
}
