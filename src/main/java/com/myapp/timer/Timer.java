package com.myapp.timer;

/**
 * Created by gaorui on 17/1/9.
 */


import com.myapp.client.Client;
import com.myapp.main.main;
import com.myapp.redis.LoadMemory;



public class Timer {
    final public static String job_name_1 = "task_Client";
    final public static String job_name_2 = "task2_Main";
    final public static String job_name_3 = "task3_Redis";

    public Timer() {
    }

    public static void main(String[] args) throws InterruptedException {
        //System.out.println("on my god");

        QuartzManager.addJob(job_name_1, Client.class, "0 0/1 * * * ?");
        Thread.sleep(1000 * 50);
        QuartzManager.addJob(job_name_2, main.class, "0/10 * * * * ?");
        QuartzManager.addJob(job_name_3, LoadMemory.class, "0 0 2 * * ?");


    }
}

