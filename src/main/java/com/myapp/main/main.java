package com.myapp.main;

import com.myapp.client.Client;
import com.myapp.proxy.HttpProxy;
import com.myapp.proxy.ProxyPool;
import com.myapp.util.HttpStatus;
import com.myapp.util.ProxyIpCheck;

import java.util.TimerTask;

/**
 * Created by gaorui on 16/12/28.
 */
public class main extends TimerTask {

    public void run() {
        ProxyPool proxyPool = Client.proxyPool;
        System.out.println("#####爬虫ip池开始测试#####");

        for (int i = 0; i < proxyPool.getIdleNum(); i++) {
            HttpProxy httpProxy = proxyPool.borrow();
            HttpStatus code = ProxyIpCheck.Check(httpProxy.getProxy());
            System.err.println(httpProxy.getProxy());
            proxyPool.reback(httpProxy, code); // 使用完成之后，归还 Proxy,并将请求结果的 http 状态码一起传入


        }

        System.out.println("#####爬虫ip池测试完成#####");
        proxyPool.allProxyStatus();  // 可以获取 ProxyPool 中所有 Proxy 的当前状态
    }

    public main() {


    }
}
