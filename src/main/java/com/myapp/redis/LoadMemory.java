package com.myapp.redis;

import com.myapp.client.CrawlClient;
import com.myapp.proxy.ProxyPool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by gaorui on 17/1/9.
 */
public class LoadMemory implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Set<String> set = JedisUtils.getProxyIp();
        Iterator iterator = set.iterator();
        ProxyPool proxyPool = CrawlClient.proxyPool;
        while (iterator.hasNext()) {
            String proxyIp = iterator.next().toString().substring(8).split(":")[0];
            int proxyPort = Integer.valueOf(iterator.next().toString().substring(8).split(":")[1]);
            proxyPool.add(proxyIp, proxyPort);

        }

    }
}
