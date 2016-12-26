package com.myapp.crawer.impl;


import com.myapp.crawer.ProxyIpCrawer;
import com.myapp.entity.ProxyIp;
import com.myapp.util.CrawerBase;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by gaorui on 16/12/26.
 */
public class ProxyIpCrawerImpl  extends ProxyIpCrawer{
	

	public ProxyIpCrawerImpl(String notIp) {
		super(notIp,"http://ip84.com/dlgn/");
		 
	}

	@Override
	public void fetchProxyIp() {
		// 只抓一页
		 for(int page = 1; page<2 ; page++){
			 fetchProxyIpOnePage(page);
		 }
		 System.out.println("allProxyIps  size :"+this.allProxyIps.size());
		
	}
    public void fetchProxyIpOnePage( int page){
    	Document doc  = CrawerBase.get(this.website+page);
		Elements items=doc.select("table.list>tbody>tr:gt(0)");
		String ip,port,area,type;
		for(Element item : items ){
			ip=item.select(">td:eq(0)").text();
			port=item.select(">td:eq(1)").text();
			area=item.select(">td:eq(2)").text();
			type=item.select(">td:eq(4)").text();
			//System.out.println("area : "+area);
			this.allProxyIps.add(new ProxyIp(ip, Integer.parseInt(port), area, type));// 出错就异常结束
	    }
        
		
	}
}
