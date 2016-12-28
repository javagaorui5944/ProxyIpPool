package com.myapp.client;


import com.myapp.crawer.ProxyIpCrawer;
import com.myapp.entity.ProxyIp;
import com.myapp.proxy.ProxyPool;


import java.util.List;
import java.util.TimerTask;

/**
 * Created by gaorui on 16/12/26.
 */
public class Client extends TimerTask {

    public ProxyIpCrawer proxyIpCrawer;
    private int count = 0;

    public static ProxyPool proxyPool;

    public Client(ProxyIpCrawer proxyIpCrawer) {

        this.proxyIpCrawer = proxyIpCrawer;
    }


    public void run() {
        count++;
        System.out.println("#####第" + count + "次开始爬取#####");
        this.proxyIpCrawer.fetchProxyIp();
        List<ProxyIp> allProxyIps = this.proxyIpCrawer.allProxyIps;
        proxyPool = new ProxyPool();
        for (ProxyIp Proxyip : allProxyIps) {

            System.out.println("proxyPool:" + Proxyip.getIp() + ":" + Proxyip.getPort());

            proxyPool.add(Proxyip.getIp(), Proxyip.getPort());


        }
        System.out.println("#####爬取完毕#####");

    }
}
