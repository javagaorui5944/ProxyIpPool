package com.myapp.timer;

/**
 * Created by gaorui on 17/1/9.
 */


import com.myapp.client.CrawlClient;
import com.myapp.main.MaintenanceService;
import com.myapp.redis.LoadMemory;


public class Timer {
    final public static String job_name_1 = "task_Client";
    final public static String job_name_2 = "task2_Main";
    final public static String job_name_3 = "task3_Redis";

    public Timer() {
    }

    public static void main(String[] args) throws InterruptedException {
        try{


        QuartzManager.addJobT(job_name_1, CrawlClient.class);
        Thread.sleep(1000 * 60);
        QuartzManager.addJob(job_name_2, MaintenanceService.class, "0 0/1 * * * ?");
        QuartzManager.addJob(job_name_3, LoadMemory.class, "0 0 2 * * ?");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

