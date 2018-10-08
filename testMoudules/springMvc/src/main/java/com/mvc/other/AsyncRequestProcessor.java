package com.mvc.other;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;

public class AsyncRequestProcessor implements Runnable {
    private AsyncContext asyncContext;
    private int milliseconds;

    public AsyncRequestProcessor() {

    }

    public AsyncRequestProcessor(AsyncContext asyncContext, int milliseconds) {
        this.asyncContext = asyncContext;
        this.milliseconds = milliseconds;
    }

    @Override
    public void run() {
        System.out.println("Async Supported? "
                + asyncContext.getRequest().isAsyncSupported());
        longProcessing(milliseconds);
        try {
            PrintWriter out = asyncContext.getResponse().getWriter();
            out.write("Processing done for " + milliseconds + " milliseconds!!");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        asyncContext.complete();
    }

    private void longProcessing(int secs) {
        // wait for given time before finishing
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
