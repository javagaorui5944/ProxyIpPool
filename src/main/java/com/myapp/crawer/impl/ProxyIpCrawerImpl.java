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
public class ProxyIpCrawerImpl extends ProxyIpCrawer {


    public ProxyIpCrawerImpl(String notIp) {


        super(notIp, "http://www.ip181.com/");
    }

    @Override
    public void fetchProxyIp() {
        // 只抓一页
        /*for (int page = 1; page < 2; page++) {
            fetchProxyIpOnePage(page);
        }*/
        fetchProxyIpOnePage();
//		 System.out.println("allProxyIps  size :"+this.allProxyIps.size());

    }

    public void fetchProxyIpOnePage() {
        Document doc = CrawerBase.get(this.website);
//        System.out.println(doc);

        Elements trs = doc.select("table").select("tr");
        for(int i = 1;i<10;i++){
            Elements tds = trs.get(i).select("td");



            String ip = tds.get(0).text();
            int port = Integer.parseInt(tds.get(1).text());

            this.allProxyIps.add(new ProxyIp(ip, port, null, null));
        }
      /*  Elements items = doc.select("table.list>tbody>tr:gt(0)");
        String ip, port, area, type;
        for (Element item : items) {
//          System.out.println(item.select(">td:eq(0)").text());
            ip = item.select(">td:eq(0)").text();
            port = item.select(">td:eq(1)").text();
            area = item.select(">td:eq(2)").text();
            type = item.select(">td:eq(4)").text();
            //System.out.println("area : "+area);
            this.allProxyIps.add(new ProxyIp(ip, Integer.parseInt(port), area, type));// 出错就异常结束
        }*/


    }
}
