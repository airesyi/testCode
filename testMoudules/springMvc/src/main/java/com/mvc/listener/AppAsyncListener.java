package com.mvc.listener;

import com.alibaba.druid.util.StringUtils;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class AppAsyncListener implements AsyncListener {
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
