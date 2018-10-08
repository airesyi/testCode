package com.mvc.controller;

import com.mvc.listener.AppAsyncListener;
import com.mvc.other.AsyncRequestProcessor;
import com.mvc.service.TestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
public class Test2 {
    @Autowired
    TestService2 testService2;

    @RequestMapping(value = "syncServlet")
    public void syncServlet(HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        System.out.println("LongRunningServlet Start::Name="
                + Thread.currentThread().getName() + "::ID="
                + Thread.currentThread().getId());
        int milliseconds = 200;
        longProcessing(milliseconds);

        PrintWriter out = response.getWriter();
        long endTime = System.currentTimeMillis();
        out.write("Processing done for " + milliseconds + " milliseconds!!");
        System.out.println("LongRunningServlet Start::Name="
                + Thread.currentThread().getName() + "::ID="
                + Thread.currentThread().getId() + "::Time Taken="
                + (endTime - startTime) + " ms.");
        out.flush();
        out.close();
    }

    private void longProcessing(int secs) {
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "asyncServlet")
    public void asyncServlet(HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        System.out.println("AsyncLongRunningServlet Start::Name="
                + Thread.currentThread().getName() + "::ID="
                + Thread.currentThread().getId());

        request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        int secs = 20;
        AsyncContext asyncCtx = request.startAsync();
        asyncCtx.addListener(new AppAsyncListener());
        asyncCtx.setTimeout(20000);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) request.getServletContext().getAttribute("executor");
        executor.execute(new AsyncRequestProcessor(asyncCtx, secs));
        long endTime = System.currentTimeMillis();
        System.out.println("AsyncLongRunningServlet End::Name="
                + Thread.currentThread().getName() + "::ID="
                + Thread.currentThread().getId() + "::Time Taken="
                + (endTime - startTime) + " ms.");
    }

    @RequestMapping(value = "ehCache")
    @ResponseBody
    public Object enCache(String param) {
        return testService2.testService2Impl(param);
    }

}
