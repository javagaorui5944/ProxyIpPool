package com.myapp.util;


import java.io.IOException;
import java.net.*;

/**
 * Created by gaorui on 16/12/26.
 */
public class ProxyIpCheck {
    private final static int DEFAULT_REUSE_TIME_INTERVAL = 1500;// ms，从一次请求结束到再次可以请求的默认时间间隔
    private static final int HTTP_CONNECT_TIMEOUT = 1000 * 15;
    private static final int HTTP_ReadTimeout =  1000 * 3;

    public static HttpStatus Check(Proxy proxy) {


        URL url = null;

        try {

            //测试url
            url = new URL("http://www.baidu.com/");
            HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);
            uc.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
            uc.setReadTimeout(HTTP_ReadTimeout);
            uc.connect();
            int code = uc.getResponseCode();
//          System.err.println(proxy+"resCode:"+code);

            if (code == 200)
                return HttpStatus.SC_OK;
            else if (code == 403)
                return HttpStatus.SC_FORBIDDEN;
            else
                return HttpStatus.SC_BAD_REQUEST;

        } catch (MalformedURLException e) {
//            e.printStackTrace();
            return HttpStatus.SC_BAD_REQUEST;
        } catch (IOException e) {
//            e.printStackTrace();
            return HttpStatus.SC_REQUEST_TIMEOUT;

        }

    }


}
