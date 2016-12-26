package com.myapp.crawer;

import com.myapp.entity.ProxyIp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaorui on 16/12/26.
 */
public abstract class ProxyIpCrawer {

	public List<ProxyIp> allProxyIps;// 解析页面获取的所有proxyip
	public List<ProxyIp> workProxyIps;// 测试之后可用的 proxyip
	public String notIp; // 测试时过滤掉的本机ip
	public String website;

	public ProxyIpCrawer(String notIp,String website) {
		super();
		this.notIp = notIp;
		this.website=website;
		this.allProxyIps = new ArrayList();
		this.workProxyIps = new ArrayList();
	}

	/**
	 * 从数据库加载代理ip ,
	 */
	public void loadDB() {
		/* 数据库 使用 Ip+port两个字段作为 主键 限制重复 */
	}

	/**
	 * 网页抓取 proxy ip，放入 allProxyIps
	 */
	public abstract void fetchProxyIp();


	/**
	 * workProxyIps 存入数据库
	 */
	public void persistWorkProxyIpsDB() {

	}

}
