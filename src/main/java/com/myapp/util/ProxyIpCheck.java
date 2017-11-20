package com.myapp.util;


import com.myapp.timer.QuartzManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.util.Random;

/**
 * Created by gaorui on 16/12/26.
 */
public class ProxyIpCheck {
    private final static int DEFAULT_REUSE_TIME_INTERVAL = 1500;// ms，从一次请求结束到再次可以请求的默认时间间隔
    private static final int HTTP_CONNECT_TIMEOUT = 1000 * 3;
    private static final int HTTP_READ_TIMEOUT = 1000 * 3;
    private static Logger logger = Logger.getLogger(ProxyIpCheck.class);

    public static HttpStatus Check(Proxy proxy) {
        URL url = null;
        HttpURLConnection uc = null;
        try {
            url = new URL("http://www.baidu.com/");
            uc = (HttpURLConnection) url.openConnection(proxy);
            String userAgent = CrawerBase.ua[new Random().nextInt(CrawerBase.ua.length - 1)];
            uc.setRequestProperty("User-Agent", userAgent);
            uc.setReadTimeout(HTTP_READ_TIMEOUT);
            uc.setConnectTimeout(HTTP_CONNECT_TIMEOUT);

            uc.connect();
            int code = uc.getResponseCode();

            if (code == 200)
                return HttpStatus.SC_OK;
            else if (code == 403)
                return HttpStatus.SC_FORBIDDEN;
            else
                return HttpStatus.SC_BAD_REQUEST;

        } catch (Exception e) {
            return HttpStatus.SC_BAD_REQUEST;

        } finally {
            uc.disconnect();
        }

    }


}
