package com.myapp.util;

import com.myapp.crawler4j.Controller;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * Created by 杨凯文 on 2017/11/17 0017.
 */
public class Executor{
    private static Logger logger = Logger.getLogger(Executor.class);
    private static ThreadPoolExecutor threadPool = null;
    private Executor(){}
    /**
     * Cannot instantiate.
     */
    public static ThreadPoolExecutor newMyexecutor(int size) {
        if(threadPool == null){
            synchronized( Executor.class ) {
                if( threadPool == null ) {

                    threadPool = new ThreadPoolExecutor(size, size + 10,
                            1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(16)) {
                        protected void afterExecute(Runnable r, Throwable t) {
                            super.afterExecute(r, t);
                            printException(r, t);
                        }
                    };
                }
            }

        }

        return  threadPool;
    }


    private static void printException(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone())
                    future.get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null)
            logger.error(t.getMessage(), t);
    }
}
