package com.myapp.client;



import com.myapp.crawler4j.Controller;
import com.myapp.proxy.ProxyPool;
import org.quartz.*;


import java.util.List;
/**
 * Created by gaorui on 16/12/26.
 */
@DisallowConcurrentExecution
public class Client implements StatefulJob{

    //public ProxyIpCrawer proxyIpCrawer = new ProxyIpCrawerImpl();
    private static int count = 0;

    public static ProxyPool proxyPool = new ProxyPool();


   /* public Client(ProxyIpCrawer proxyIpCrawer) {

        this.proxyIpCrawer = proxyIpCrawer;
    }*/


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        count++;
        System.out.println("#####第" + count + "次开始爬取#####");
        Controller.fetchProxyIp();
/*        List<ProxyIp> allProxyIps = this.proxyIpCrawer.allProxyIps;
            for (ProxyIp Proxyip : allProxyIps) {

                System.out.println("proxyPool:" + Proxyip.getIp() + ":" + Proxyip.getPort());
                proxyPool.add(Proxyip.getIp(), Proxyip.getPort());
            }*/
        System.out.println("#####爬取完毕#####");
    }
}
