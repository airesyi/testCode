package com.mvc.controller;

import com.alibaba.druid.util.StringUtils;
import com.mvc.listener.AppAsyncListener;
import com.mvc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class Test {
    @Autowired
    TestService testService;

    @RequestMapping(value = "test1")
    @ResponseBody
    public Object test1(String gTicket) {
        return testService.testMethod1(gTicket);
    }

    @RequestMapping(value = "testInclude")
    @ResponseBody
    public Object testInclude(String gTicket) {
        return testService.testInclude();
    }

    @RequestMapping(value = "test2")
    @ResponseBody
    public Object test2(String gTicket) {
        return testService.testMethod2(gTicket);
    }

    @RequestMapping(value = "test3")
    @ResponseBody
    public Object test3(String gTicket) {
        System.out.println("controller:" + Thread.currentThread().getId());
        long start = System.currentTimeMillis();
        String a = testService.testMethod3(gTicket);
        long end = System.currentTimeMillis();
        System.out.println("controller:" + Thread.currentThread().getId() + ". time:" + (end - start));
        return a;
    }

    @RequestMapping(value = "test4")
    @ResponseBody
    public Object test4(String gTicket) {
        System.out.println("controller:" + Thread.currentThread().getId());
        long start = System.currentTimeMillis();
        testService.testMethod4(gTicket);
        long end = System.currentTimeMillis();
        System.out.println("controller:" + Thread.currentThread().getId() + ". time:" + (end - start));
        return "test4";
    }

    @RequestMapping(value = "test5")
    @ResponseBody
    public Object test5(String gTicket) {
        return testService.testMethod5(gTicket);
    }

    @RequestMapping(value = "/testAsyn", method = RequestMethod.GET)
    public void testAsny(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("start");
        System.out.println("controller:" + Thread.currentThread().getId());
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(3000);
        asyncContext.addListener(new AppAsyncListener());
        new Thread(new Work(asyncContext, request)).start();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("执行中");
        System.out.println("controller:" + Thread.currentThread().getId());
    }

    class Work implements Runnable {

        private AsyncContext asyncContext;
        private HttpServletRequest request;

        public Work(AsyncContext asyncContext, HttpServletRequest request) {
            this.asyncContext = asyncContext;
            this.request = request;
        }

        @Override
        public void run() {
            try {
                System.out.println("run:" + Thread.currentThread().getId());
                Thread.sleep(1);
                //此处通过观察源码，得知需要从request来判断是否超时，否则会一直抛出异常,超时的话，超时参数会置为-1
                if (request.getAsyncContext() != null && asyncContext.getTimeout() > 0) {
                    try {
                        ServletResponse response = asyncContext.getResponse();
                        response.setContentType("application/json; charset=utf-8");
                        response.setCharacterEncoding("UTF-8");
                        PrintWriter out = response.getWriter();
                        out.println("后台执行完成");
//                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    asyncContext.complete();
                }
                System.out.println("run:" + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    class MyAsyncListener implements AsyncListener {

        @Override
        public void onComplete(AsyncEvent asyncEvent) throws IOException {
            PrintWriter out = null;
            try {
                AsyncContext asyncContext = asyncEvent.getAsyncContext();
                ServletResponse response = asyncContext.getResponse();
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                ServletRequest request = asyncContext.getRequest();
                out = response.getWriter();
                if (request.getAttribute("timeout") != null &&
                        StringUtils.equals("true", request.getAttribute("timeout").toString())) {//超时
                    out.println("后台线程执行超时---【回调】");
                    System.out.println("异步servlet【onComplete超时】");
                } else {//未超时
                    out.println("后台线程执行完成---【回调】");
                    System.out.println("异步servlet【onComplete完成】");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }

        @Override
        public void onError(AsyncEvent asyncEvent) throws IOException {
            System.out.println("异步servlet错误");
        }

        @Override
        public void onStartAsync(AsyncEvent arg0) throws IOException {
            System.out.println("开始异步servlet");
        }

        @Override
        public void onTimeout(AsyncEvent asyncEvent) throws IOException {
            ServletRequest request = asyncEvent.getAsyncContext().getRequest();
            request.setAttribute("timeout", "true");
            System.out.println("异步servlet【onTimeout超时】");
        }

    }

}