package com.myapp.util;

import com.myapp.entity.ProxyIp;

import java.io.IOException;
import java.net.*;

/**
 * Created by gaorui on 16/12/26.
 */
public class ProxyIpCheck {
    private final static int DEFAULT_REUSE_TIME_INTERVAL = 1500;// ms，从一次请求结束到再次可以请求的默认时间间隔
    private static final int HTTP_CONNECT_TIMEOUT = 10 * 1000;
    private static final int HTTP_SOCKET_TIMEOUT = 10 * 1000;

    public static HttpStatus Check(Proxy proxy) {


        URL url = null;

        try {

            //测试url
            url = new URL("http://www.kuaidaili.com/check/");
            HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);
            uc.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
            uc.setReadTimeout(HTTP_SOCKET_TIMEOUT);
            uc.connect();
            int code = uc.getResponseCode();
            if (code == 200)
                return HttpStatus.SC_OK;
            else if (code == 403)
                return HttpStatus.SC_FORBIDDEN;
            else
                return HttpStatus.SC_BAD_REQUEST;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return HttpStatus.SC_BAD_REQUEST;
        }

    }


}
