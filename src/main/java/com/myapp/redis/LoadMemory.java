package com.myapp.redis;

import com.myapp.client.Client;
import com.myapp.proxy.ProxyPool;
import com.myapp.timer.QuartzManager;
import com.myapp.timer.Timer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by gaorui on 17/1/9.
 */
public class LoadMemory implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        QuartzManager.pauseTrigger(Timer.job_name_2);
        Jedis jedis = RedisStorage.getInstance();
        Set<String> set = jedis.keys("*");

        Iterator iterator = set.iterator();
        ProxyPool proxyPool = Client.proxyPool;
        while (iterator.hasNext()) {
            String proxyIp = iterator.next().toString().substring(8).split(":")[0];
            int proxyPort = Integer.valueOf(iterator.next().toString().substring(8).split(":")[1]);
            proxyPool.add(proxyIp, proxyPort);
//            jedis.del(iterator.next().toString());

        }

//        QuartzManager.rescheduleJob(Timer.job_name_2);
    }
}
