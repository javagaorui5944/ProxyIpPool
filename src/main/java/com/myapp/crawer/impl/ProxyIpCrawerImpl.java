package com.myapp.crawer.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.crawer.ProxyIpCrawer;
import com.myapp.entity.ProxyIp;
import com.myapp.util.CrawerBase;
import com.myapp.util.HttpUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.math.BigDecimal;

/**
 * Created by gaorui on 16/12/26.
 */
public class ProxyIpCrawerImpl extends ProxyIpCrawer {

    JedisPool pool = new  JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
    public ProxyIpCrawerImpl() {


        super( "http://www.ip181.com/");
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
        //System.out.println(doc);

        Elements trs = doc.select("table").select("tr");
        for(int i = 1;i<50;i++){
            Elements tds = trs.get(i).select("td");

            String ip = tds.get(0).text();
            int port = Integer.parseInt(tds.get(1).text());
            String area = tds.get(5).text();
            this.allProxyIps.add(new ProxyIp(ip, port, area, null));

            Jedis jedis = pool.getResource();
            //JSONObject jsonObject = new JSONObject();
            //jsonObject.put("data-view",);
            area = area.replaceAll("[a-zA-Z]","" ).replaceAll(" ","");
            System.out.print(area);
            String strResult = HttpUtil.sendGet("http://maps.google.cn/maps/api/geocode/json","address="+area+"&sensor=false");
            JSONObject jsonObject = JSON.parseObject(strResult);
            JSONArray jsonArray = (JSONArray) jsonObject.get("results");
            if(jsonArray.size()>0){
                JSONObject jsonObject1 = (JSONObject)jsonArray.get(0);
                JSONObject jsonObject2 = (JSONObject)jsonObject1.get("geometry");
                JSONObject jsonObject3 = (JSONObject)jsonObject2.get("viewport");
                JSONObject jsonObject4 = (JSONObject)jsonObject3.get("northeast");
                BigDecimal lat = (BigDecimal) jsonObject4.get("lat");
                BigDecimal lng = (BigDecimal) jsonObject4.get("lng");
                jedis.publish("channel1", ip+":"+port+"#"+area+"@"+lat+"$"+lng);
                System.out.println("可视化");
                //i = jedis.publish("channel2", "你好呀，亲");
                //System.out.println(i+" 个订阅者接受到了 channel2 消息");
                pool.returnResource(jedis);
            }

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
