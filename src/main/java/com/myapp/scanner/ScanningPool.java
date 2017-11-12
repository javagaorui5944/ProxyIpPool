package com.myapp.scanner;

import com.myapp.client.Client;
import com.myapp.proxy.HttpProxy;
import com.myapp.util.CrawerBase;
import com.uwyn.jhighlight.tools.StringUtils;
import org.apache.poi.util.StringUtil;

import java.io.InputStream;
import java.net.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gaorui on 17/4/24.
 */
public class ScanningPool {

    public static boolean b = true;

    public static void scanningProxyIp(HttpProxy httpProxy) {
        b = false;
        Proxy proxy = httpProxy.getProxy();
        String str = proxy.toString();
        String filterIp = str.substring(str.indexOf("/") + 1, str.indexOf(":"));
        str = str.substring(str.indexOf("/") + 1, str.indexOf(".", str.indexOf(".") + 1));
        System.out.println("*扫描ip段ing:" + str);
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
            System.out.println("url invalidate");
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
                Client.proxyPool.add(ip, port);
                System.out.println(addr.toString() + "is ok");
            }

        } catch (Exception e) {
            //System.out.println("ip " + ip + " is not aviable");
        }
    }


}
