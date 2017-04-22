package com.myapp.main;

import com.myapp.client.Client;
import com.myapp.proxy.HttpProxy;
import com.myapp.proxy.ProxyPool;
import com.myapp.util.HttpStatus;
import com.myapp.util.ProxyIpCheck;
import org.quartz.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by gaorui on 16/12/28.
 */
@DisallowConcurrentExecution
public class main implements StatefulJob {
    ProxyPool proxyPool = null;
    private static int count = 0;
    private Integer countLock = 0;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        count++;
        proxyPool = Client.proxyPool;
        System.out.println("#####爬虫ip池第" + count + "次开始测试#####");
        int idleNum = proxyPool.getIdleNum();
        System.out.println("###idleNum:" + idleNum + "###");
        int size = (idleNum * 10) / (60 * 2);
        int z = 0;
        if (size != 0) {
            z = idleNum / size;
        }
        countLock = z;
        System.out.println("size:" + size);
        for (int j = 0; j < size; j++) {
            //count++;
            A a = new A(j, z);
            Thread t1 = new Thread(a);
            t1.setName(String.valueOf(j));
            t1.start();
            //list_Thread.add(t1);
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
   /*     for (Thread thread : list_Thread) {

            while (thread.isAlive()) ;
        }*/
        //list_Thread.removeAll(list_Thread);


        try {
            countLock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("#####爬虫ip池第" + count + "次测试完成#####");
        proxyPool.allProxyStatus();  // 可以获取 ProxyPool 中所有 Proxy 的当前状态
    }

    class A implements Runnable {

        int j;
        int z;

        public A(int j, int z) {

            this.j = j;
            this.z = z;
        }

        @Override
        public void run() {
            System.out.println("#####多线程分片跑区间:" + (j * z + 1) + "-" + ((j + 1) * z));
            for (int i = j * z + 1; i < (j + 1) * z; i++) {
                HttpProxy httpProxy = proxyPool.borrow();
                HttpStatus code = ProxyIpCheck.Check(httpProxy.getProxy());
                System.err.println("name:" + Thread.currentThread().getName() + httpProxy.getProxy() + ":" + code);

                proxyPool.reback(httpProxy, code); // 使用完成之后，归还 Proxy,并将请求结果的 http 状态码一起传入


            }
        }
    }
}
