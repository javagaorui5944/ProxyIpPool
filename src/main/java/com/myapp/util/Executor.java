package com.myapp.util;

import java.util.concurrent.*;

/**
 * Created by 杨凯文 on 2017/11/17 0017.
 */
public class Executor{
    /**
     * Cannot instantiate.
     */
    public static ExecutorService newMyexecutor(int size) {
        return new ThreadPoolExecutor(size,size+10,
                1, TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(16));
    }
}
