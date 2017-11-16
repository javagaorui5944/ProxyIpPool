package com.myapp.scanner;

import com.myapp.client.CrawlClient;
import com.myapp.proxy.HttpProxy;
import com.myapp.util.CrawerBase;
import org.apache.log4j.Logger;

import java.net.*;
import java.util.Random;

/**
 * Created by gaorui on 17/4/24.
 */
public class ScanningPool {

    public static boolean b = true;
    private static Logger logger = Logger.getLogger(ScanningPool.class);

    public static void scanningProxyIp(HttpProxy httpProxy) {
        b = false;
        Proxy proxy = httpProxy.getProxy();
        String str = proxy.toString();
        String filterIp = str.substring(str.indexOf("/") + 1, str.indexOf(":"));
        str = str.substring(str.indexOf("/") + 1, str.indexOf(".", str.indexOf(".") + 1));
        logger.info("*扫描ip段ing:" + str);
        int a[] = {80, 8080, 3128, 8081, 9080};
        String ip;
        for (int i = 0; i < 255; i++) {
            for (int j = 0; j < 255; j++) {

                ip = str + "." + i + "." + j;
                if (!filterIp.equals(ip)) {
                    for (int port : a) {

                        createIPAddress(ip, port);
                    }
                }
            }
        }
        b = true;
    }

    public static void createIPAddress(String ip, int port) {
        URL url = null;
        try {
            url = new URL("http://www.baidu.com/");
        } catch (MalformedURLException e) {
            logger.error("url invalidate");
            return;
        }
        InetSocketAddress addr = null;
        addr = new InetSocketAddress(ip, port);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
            conn.setConnectTimeout(5000);

            String userAgent = CrawerBase.ua[new Random().nextInt(CrawerBase.ua.length - 1)];
            conn.setRequestProperty("User-Agent", userAgent);
            conn.connect();
            int code = conn.getResponseCode();

            if (code == 200) {
                CrawlClient.proxyPool.add(ip, port);
                logger.info(addr.toString() + "is ok");
            }

        } catch (Exception e) {
        }
    }


}
