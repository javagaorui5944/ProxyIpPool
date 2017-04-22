import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

class DelayedTask implements Runnable, Delayed {

    private static int counter = 0;
    protected static List<DelayedTask> sequence = new ArrayList<DelayedTask>();
    public final int id = counter++;
    private final int delayTime;
    private final long triggerTime;

    public DelayedTask(int delayInMillis) {
        delayTime = delayInMillis;
        triggerTime = System.nanoTime() + NANOSECONDS.convert(delayTime, MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask that = (DelayedTask) o;
        if (triggerTime < that.triggerTime) return -1;
        if (triggerTime > that.triggerTime) return 1;
        return 0;
    }

    /**
     * 剩余的延迟时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(triggerTime - System.nanoTime(), NANOSECONDS);
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4d]", delayTime) + " Task " + id;
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService exec;

        public EndSentinel(int delay, ExecutorService exec) {
            super(delay);
            this.exec = exec;
        }

        @Override
        public void run() {
            System.out.println(this + " calling shutDownNow()");
            exec.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> tasks;

    public DelayedTaskConsumer(DelayQueue<DelayedTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                tasks.take().run();//run tasks with current thread.
            }
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
        System.out.println("Finished DelaytedTaskConsumer.");
    }
}


public class DelayQueueDemo {
    public static void main(String[] args) {
        int maxDelayTime = 1000*5;//milliseconds
        Random random = new Random();
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();
        //填充10个休眠时间随机的任务
        for (int i = 1; i < 10; i++) {
            //if(i == 10) queue.put(new DelayedTask(maxDelayTime));
            DelayedTask delayedTask = new DelayedTask(random.nextInt(maxDelayTime));
            queue.put(delayedTask);

            System.out.println(" Task " + delayedTask.id+":"+delayedTask.getDelay(TimeUnit.NANOSECONDS));
        }
        try {
            //System.out.println(queue.size());
            /*while(queue.size()>0)
            {
                DelayedTask de = queue.take();
                System.out.println(de.id);
            }*/
            for (int i = 1; i < 5; i++) {
                DelayedTask de = queue.take();
                System.out.println(de.id);
            }
            for (int i = 6; i < 10; i++) {
                DelayedTask de = queue.take();
                System.out.println(de.id);
            }



        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //设置结束的时候。
        //queue.add(new DelayedTask.EndSentinel(maxDelayTime, exec));
        //exec.execute(new DelayedTaskConsumer(queue));
    }
}