package com.myapp.util;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Random;

/**
 * Created by gaorui on 16/12/26.
 */
public class CrawerBase {
    private static Logger logger = Logger.getLogger(CrawerBase.class);

    public static final String[] ua = {"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; WOW64; Trident/6.0)",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; ARM; Trident/6.0)",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8) AppleWebKit/536.25 (KHTML, like Gecko) Version/6.0 Safari/536.25"};
    private static Random random;

    public static Document get(String url, int trys) throws IOException {
        CrawerBase.random = new Random();
        try {
            Connection connection = Jsoup.connect(url);
            connection.timeout(3000);
            String userAgent = ua[random.nextInt(ua.length - 1)];// 随机设置，但部分站点存在高频解析不一致 问题。
            //String userAgent = ua[4];   // 设置 固定值 可解决这一问题。
            connection.userAgent(userAgent);

            return connection.get();
        } catch (IOException e) {
            logger.error("try connect the page:" + url + ",try times:" + trys);
            if (trys-- != 0) {
                return get(url, trys);
            }
            throw e;
        }
    }

    public static Document get(String url) {
        int trys = 3;
        try {
            return get(url, trys);
        } catch (Exception e) {

        }
        // 4次请求之后无法解析返回空文档
        return new Document("");
    }


    public static Document proxyGet(String url, int trys, String ip, int port) throws IOException {
        CrawerBase.random = new Random();
        try {
            Connection connection = Jsoup.connect(url);
            connection.timeout(1000);
            String userAgent = ua[random.nextInt(ua.length - 1)];
            connection.userAgent(userAgent);
//			connection(ip, port);
            return connection.get();
        } catch (IOException e) {
            logger.error("try connect the page:" + url + ",try times:" + trys);
            if (trys-- != 0) {
                return get(url, trys);
            }
            throw e;
        }
    }

    public static Document proxyGet(String url, String ip, int port) {
        int trys = 3;
        try {
            return proxyGet(url, trys, ip, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 4次请求之后无法解析返回空文档
        return new Document("");
    }


}
