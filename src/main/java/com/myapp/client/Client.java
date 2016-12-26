package com.myapp.client;


import com.myapp.crawer.ProxyIpCrawer;
import com.myapp.entity.ProxyIp;
import com.myapp.proxy.HttpProxy;
import com.myapp.proxy.ProxyPool;
import com.myapp.util.HttpStatus;
import com.myapp.util.ProxyIpCheck;

import java.util.List;
import java.util.TimerTask;

/**
 * Created by gaorui on 16/12/26.
 */
public class Client  extends TimerTask{
	
	public ProxyIpCrawer proxyIpCrawer;
    public Client(ProxyIpCrawer proxyIpCrawer) {

		this.proxyIpCrawer = proxyIpCrawer;
	}

	public Client() {

	}

	public void run() {

        this.proxyIpCrawer.fetchProxyIp();
		List<ProxyIp> allProxyIps =  this.proxyIpCrawer.allProxyIps;
		ProxyPool proxyPool =new ProxyPool();
		for(ProxyIp Proxyip: allProxyIps){

		System.err.println("proxyPool:"+Proxyip.getIp()+":"+Proxyip.getPort());

			proxyPool.add(Proxyip.getIp(),Proxyip.getPort());

		}
		System.err.println("===============");

		for(int i= 0; i<proxyPool.getIdleNum();i++){
			HttpProxy httpProxy  = proxyPool.borrow();
			HttpStatus code = ProxyIpCheck.Check(httpProxy.getProxy());
			System.err.println(httpProxy.getProxy());
			proxyPool.reback(httpProxy,code); // 使用完成之后，归还 Proxy,并将请求结果的 http 状态码一起传入


		}
		proxyPool.allProxyStatus();  // 可以获取 ProxyPool 中所有 Proxy 的当前状态

	}
}
