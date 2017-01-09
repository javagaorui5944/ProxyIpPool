package com.myapp.proxy;

import com.myapp.redis.RedisStorage;
import com.myapp.util.HttpStatus;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;


import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

/**
 * Created by gaorui on 16/12/26.
 */
public class ProxyPool {


    private BlockingQueue<HttpProxy> idleQueue = new DelayQueue<HttpProxy>(); // 存储空闲的Proxy
    private Map<String, HttpProxy> totalQueue = new ConcurrentHashMap<String, HttpProxy>(); // 存储所有的Proxy


    /**
     * 添加Proxy
     *
     * @param httpProxies
     */
    public void add(HttpProxy... httpProxies) {
        for (HttpProxy httpProxy : httpProxies) {
//            System.out.println(httpProxy.getKey());
            if (totalQueue.containsKey(httpProxy.getKey())) {
                continue;
            }

//                httpProxy.success();
                idleQueue.add(httpProxy);
                totalQueue.put(httpProxy.getKey(), httpProxy);

        }
    }

    public void add(String address, int port) {

        this.add(new HttpProxy(address, port));
    }

    /**
     * 得到Proxy
     *
     * @return
     */
    public HttpProxy borrow() {
        HttpProxy httpProxy = null;
        try {
            Long time = System.currentTimeMillis();
            httpProxy = idleQueue.take();
            double costTime = (System.currentTimeMillis() - time) / 1000.0;

            HttpProxy p = totalQueue.get(httpProxy.getKey());
            p.borrow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (httpProxy == null) {
            throw new NoSuchElementException();
        }
        return httpProxy;
    }

    /**
     * 反馈 Proxy
     *
     * @param httpProxy
     * @param httpStatus
     */
    public void reback(HttpProxy httpProxy, HttpStatus httpStatus) {
        switch (httpStatus) {
            case SC_OK:
                httpProxy.success();
                httpProxy.setReuseTimeInterval(HttpProxy.DEFAULT_REUSE_TIME_INTERVAL);
                break;
            case SC_FORBIDDEN:
                httpProxy.fail(httpStatus);
                httpProxy.setReuseTimeInterval(HttpProxy.DEFAULT_REUSE_TIME_INTERVAL * (httpProxy.getFailedNum()+1)); // 被网站禁止，调节更长时间的访问频率
//                logger.info(httpProxy.getProxy() + " >>>> reuseTimeInterval is >>>> " + TimeUnit.SECONDS.convert(httpProxy.getReuseTimeInterval(), TimeUnit.MILLISECONDS));
                break;
            default:
                httpProxy.fail(httpStatus);
                httpProxy.setReuseTimeInterval(HttpProxy.DEFAULT_REUSE_TIME_INTERVAL * (httpProxy.getFailedNum()+1)); // Ip可能无效，调节更长时间的访问频率
                break;
        }
        if (httpProxy.getFailedNum() > 5) { // 连续失败超过 5 次，移除代理池队列
            httpProxy.setReuseTimeInterval(HttpProxy.FAIL_REVIVE_TIME_INTERVAL);
//            logger.error("remove proxy >>>> " + httpProxy.getProxy() + ">>>>" + httpProxy.countErrorStatus() + " >>>> remain proxy >>>> " + idleQueue.size());
            return;
        }
        if (httpProxy.getFailedNum() > 0 && httpProxy.getFailedNum() % 5 == 0) { //失败超过 5次，10次，15次，检查本机与Proxy的连通性
            if (!httpProxy.check()) {
                httpProxy.setReuseTimeInterval(HttpProxy.FAIL_REVIVE_TIME_INTERVAL);
//                logger.error("remove proxy >>>> " + httpProxy.getProxy() + ">>>>" + httpProxy.countErrorStatus() + " >>>> remain proxy >>>> " + idleQueue.size());
                return;
            }
        }
        if(httpProxy.getSucceedNum()>5){
            //持久化到磁盘,提供代理ip服务
            RedisStorage.setProxyIp(httpProxy);//连续成功超过 5次，移除代理池队列,存储到redis
            return;
        }
        try {
            idleQueue.put(httpProxy);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void allProxyStatus() {
        String re = "all proxy info >>>> \n";
        for(HttpProxy httpProxy : idleQueue) {
            re += httpProxy.toString() + "\n";

        }
        System.out.print(re);
//        logger.info(re);
    }

    /**
     * 获取当前空闲的Proxy
     *
     * @return
     */
    public int getIdleNum() {

        return idleQueue.size();
    }

}
