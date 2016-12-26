

import sun.misc.IOUtils;

import java.io.*;
import java.net.*;

public class test {




    public static void main(String[] args) {
        URL url = null;

        try {
            url = new URL("http://www.baidu.com");
            // 创建代理服务器
            InetSocketAddress addr = null;
            addr = new InetSocketAddress("117.79.93.11", 8808);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            System.out.print(conn.getResponseCode());
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            Reader reader=new InputStreamReader(in);
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
            reader.close();
//            System.out.print(in.read());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}

