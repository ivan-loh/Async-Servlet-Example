package com.sstr.example;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet(
        name = "async",
        value = {"/async"},
        asyncSupported = true,
        initParams = {
                @WebInitParam(name = "JobPoolSize", value = "50")
        }
)
public class Async extends HttpServlet {

    public final int REQUEST_TIMEOUT = 60000;
    private ExecutorService exe;

    @Override
    public void init() throws ServletException {
        int size = Integer.parseInt(getInitParameter("JobPoolSize"));
        exe = Executors.newFixedThreadPool(size);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final AsyncContext context = req.startAsync();
        context.setTimeout(REQUEST_TIMEOUT);

        /**
         * Context Events
         */
        context.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                context
                        .getResponse()
                        .getWriter().write("Request Timeout");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                context
                        .getResponse()
                        .getWriter().write("Processing Error");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
            }
        });


        /**
         * Enqueue Execution
         */
        exe.execute(new ContextExecution(context));
    }

    @Override
    public void destroy() {
        exe.shutdown();
    }

    class ContextExecution implements Runnable {

        final AsyncContext context;

        public ContextExecution(AsyncContext context) {
            this.context = context;
        }

        @Override
        public void run() {
            try {

                // Simulate Time Consuming Task
                Thread.sleep(1000);

                ServletResponse resp = context.getResponse();
                if (resp != null) {
                    resp.getWriter().write("Ok");
                }

                context.complete();
            } catch (Exception e) {
                // Handle ?
            }
        }
    }
}
