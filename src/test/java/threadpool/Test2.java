package threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by gaorui on 17/4/21.
 */
public class Test2 {
    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, workQueue);
        poolExecutor.execute(new Task1());
        //poolExecutor.execute(new Task2());
        //poolExecutor.shutdown();
    }

    public static void Test(){


    }
}

class Task1 implements Runnable {

    public void run() {
        System.out.println("执行任务1");
    }
}

class Task2 implements Runnable {

    public void run() {
        System.out.println("执行任务2");
    }
}
