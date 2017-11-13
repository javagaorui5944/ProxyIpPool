package com.myapp.client;


import com.myapp.crawler4j.Controller;
import com.myapp.proxy.ProxyPool;
import org.apache.log4j.Logger;
import org.quartz.*;


/**
 * Created by gaorui on 16/12/26.
 */
@DisallowConcurrentExecution
public class Client implements StatefulJob {

    private static Logger logger = Logger.getLogger(Client.class);

    private static int count = 0;

    public static ProxyPool proxyPool = new ProxyPool();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        synchronized (Client.class) {
            count++;
        }
        logger.info("#####第" + count + "次开始爬取#####");
        Controller.fetchProxyIp();
        logger.info("#####爬取完毕#####");
    }
}
