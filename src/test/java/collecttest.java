/**
 * Created by gaorui on 17/4/16.
 */

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class collecttest {


    public static void main(String[] args) {
       /*String ip = "";
         for (int i = 0; i < 255; i++) {
            for (int j = 0; j < 255; j++) {

                ip = "103.35." + i + "." + j;
                createIPAddress(ip,80);

            }
        }*/
        //createIPAddress("61.91.89.53",80);
        //System.out.println("ss");
        String ressult = sendGet("http://www.ip181.com/");
        System.out.println(ressult);

    }

    public static void createIPAddress(String ip, int port) {
        URL url = null;
        try {
            url = new URL("http://www.baidu.com/");
        } catch (MalformedURLException e) {
            System.out.println("url invalidate");
        }
        InetSocketAddress addr = null;
        addr = new InetSocketAddress(ip, port);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
        InputStream in = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Charset", "GB2312");
            //conn.connect();
            //int code = conn.getResponseCode();
            //System.out.println(code);
            in = conn.getInputStream();

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("ip " + ip + " is not aviable");
        }
        String s = convertStreamToString(in);
        System.out.println(s);
        if (s.indexOf("baidu") > 0) {
            System.out.println(ip + " is ok");
            System.exit(0);
        }
    }

    public static String convertStreamToString(InputStream is) {
        if (is == null)
            return "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "gb2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection;
            InetSocketAddress addr = null;
            addr = new InetSocketAddress("124.88.67.39", 80);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
            connection = realUrl.openConnection(proxy);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "gb2312"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            return result;
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
