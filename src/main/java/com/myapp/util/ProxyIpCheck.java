package com.myapp.util;


import java.io.IOException;
import java.net.*;
import java.util.Random;

/**
 * Created by gaorui on 16/12/26.
 */
public class ProxyIpCheck {
    private final static int DEFAULT_REUSE_TIME_INTERVAL = 1500;// ms，从一次请求结束到再次可以请求的默认时间间隔
    private static final int HTTP_CONNECT_TIMEOUT = 1000 * 5;

    public static HttpStatus Check(Proxy proxy) {


        URL url = null;


        try {
            url = new URL("http://www.baidu.com/");
            HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);
            String userAgent = CrawerBase.ua[new Random().nextInt(CrawerBase.ua.length - 1)];
            uc.setRequestProperty("User-Agent", userAgent);
            uc.setConnectTimeout(HTTP_CONNECT_TIMEOUT);

            uc.connect();
            int code = uc.getResponseCode();

            if (code == 200)
                return HttpStatus.SC_OK;
            else if (code == 403)
                return HttpStatus.SC_FORBIDDEN;
            else
                return HttpStatus.SC_BAD_REQUEST;

        } catch (MalformedURLException e) {
            return HttpStatus.SC_BAD_REQUEST;
        } catch (IOException e) {
            return HttpStatus.SC_REQUEST_TIMEOUT;

        } finally {
        }

    }


}
