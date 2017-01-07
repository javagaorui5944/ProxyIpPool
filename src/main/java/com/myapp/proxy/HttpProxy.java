package com.myapp.proxy;

import com.myapp.util.HttpStatus;
import com.myapp.util.IpUtils;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by gaorui on 16/12/26.
 */

public class HttpProxy implements Delayed {


    public final static int DEFAULT_REUSE_TIME_INTERVAL = 1500;// ms，从一次请求结束到再次可以请求的默认时间间隔
    public final static int FAIL_REVIVE_TIME_INTERVAL = 2 * 60 * 60 * 1000; //ms,请求失败，重试的时间间隔

    private Proxy proxy;
    private InetAddress localAddr;

    private int reuseTimeInterval = 0;
    private Long canReuseTime = 0L;  // 当前Proxy可重用的时间,纳秒

    private int failedNum = 0;//失败次数
    private int borrowNum = 0;//取出次数
    private int succeedNum = 0;//成功次数

    private Map<HttpStatus, Integer> countErrorStatus = new HashMap<HttpStatus, Integer>();

    public HttpProxy(String address, int port) {
        this(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(address, port)), 0, 0, DEFAULT_REUSE_TIME_INTERVAL);
    }

    public HttpProxy(Proxy proxy) {

        this(proxy, 0, 0, DEFAULT_REUSE_TIME_INTERVAL);
    }

    public HttpProxy(Proxy proxy, int borrowNum, int failedNum, int reuseTimeInterval) {
        this.localAddr = IpUtils.getLocalAddr(); // 获取当前机器的ip地址
        if (localAddr == null) {
            //logger.error("cannot get local IP!");
            System.exit(0);
        }
        this.proxy = proxy;
        this.canReuseTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(reuseTimeInterval, TimeUnit.MILLISECONDS);
    }

    /**
     * 检查本地机器和Proxy之间的连通性
     *
     * @return
     */
    public boolean check() {
        boolean isReachable = false;
        Socket socket = null;
        try {
            socket = new Socket();
            socket.bind(new InetSocketAddress(localAddr, 0));
            socket.connect(proxy.address(), 3000);
            isReachable = true;
        } catch (Exception e) {
            //logger.error("bad proxy >>>" + this.proxy.toString());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isReachable;
    }

    public void success() {
        this.succeedNum++;
        this.failedNum = 0; //将 failNum 清零
        countErrorStatus.clear();
    }

    /**
     * 代理失败，记录相应的错误状态码
     *
     * @param httpStatus
     */
    public void fail(HttpStatus httpStatus) {
        if (countErrorStatus.containsKey(httpStatus)) {
            countErrorStatus.put(httpStatus, countErrorStatus.get(httpStatus) + 1);
        } else {
            countErrorStatus.put(httpStatus, 1);
        }
//      this.succeedNum =0;
        this.failedNum++;
    }

    /**
     * 输出错误请求的日志
     *
     * @return
     */
    public String countErrorStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<HttpStatus, Integer> entry : countErrorStatus.entrySet()) {
            stringBuilder.append(entry.getKey().name()).append("_").append(entry.getKey().getCode()).append("->").append(entry.getValue());
        }
        return stringBuilder.toString();
    }

    /**
     * 对请求进行计数
     */
    public void borrow() {
        this.borrowNum++;
    }

    public int getFailedNum() {
        return failedNum;
    }
    public int getSucceedNum() {
        return succeedNum;
    }

    public int getBorrowNum() {
        return borrowNum;
    }

    public void setReuseTimeInterval(int reuseTimeInterval) {
        this.reuseTimeInterval = reuseTimeInterval;
        this.canReuseTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(reuseTimeInterval, TimeUnit.MILLISECONDS);
    }

    public int getReuseTimeInterval() {
        return this.reuseTimeInterval;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public int compareTo(Delayed o) {
        HttpProxy that = (HttpProxy) o;
        return canReuseTime > that.canReuseTime ? 1 : (canReuseTime < that.canReuseTime ? -1 : 0);
    }

    public long getDelay(TimeUnit unit) {

        return unit.convert(canReuseTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public String toString() {
        return this.proxy.toString()
                + ">>> 使用:" + borrowNum + "次 "
                + ">>> 连续成功:" + succeedNum + "次"
                + ">>> 连续失败:" + failedNum + "次"
                + ">>> 距离下次可用:" + TimeUnit.MILLISECONDS.convert(canReuseTime > System.nanoTime() ? canReuseTime - System.nanoTime() : 0, TimeUnit.NANOSECONDS) + " ms后";
    }

    public String getKey() {
        InetSocketAddress address = (InetSocketAddress) proxy.address();
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }
}
