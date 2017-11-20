package com.myapp.main;

import com.myapp.client.CrawlClient;
import com.myapp.proxy.HttpProxy;
import com.myapp.proxy.ProxyPool;
import com.myapp.util.Executor;
import com.myapp.util.HttpStatus;
import com.myapp.util.ProxyIpCheck;
import org.apache.log4j.Logger;
import org.quartz.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by gaorui on 16/12/28.
 */
public class MaintenanceService implements StatefulJob {
    private static Logger logger = Logger.getLogger(MaintenanceService.class);

    ProxyPool proxyPool = null;
    private static int count = 0;
    private Integer countLock = 2;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        synchronized (countLock){
            count++;
        }
        try {
        proxyPool = CrawlClient.proxyPool;
        logger.info("爬虫ip池第" + count + "次开始测试");
        int idleNum = proxyPool.getIdleNum();
        logger.info("idleNum:" + idleNum);
        int size = idleNum  / 12;
        int z = 0;
        if (size != 0) {
            z = idleNum / size;
        }
        countLock = size;
        CountDownLatch countDownLatch = new CountDownLatch(countLock);

        ThreadPoolExecutor executor= Executor.newMyexecutor(size);
        executor.allowCoreThreadTimeOut(true);
        for (int j = 0; j < size; j++) {
            A a = new A(j, z,countDownLatch);
            executor.execute(a);
            logger.info("线程池中现在的线程数目是："+executor.getPoolSize()+
                    ",  队列中正在等待执行的任务数量为："+
                    executor.getQueue().size());
        }

            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }finally {
            //executor.shutdown();
        }

        logger.info("爬虫ip池第" + count + "次测试结果");
        proxyPool.allProxyStatus();  // 可以获取 ProxyPool 中所有 Proxy 的当前状态
    }

    class A implements Runnable {

        int j;
        int z;
        CountDownLatch latch;
        public A(int j, int z,CountDownLatch latch) {

            this.j = j;
            this.z = z;
            this.latch = latch;
        }

        @Override
        public void run() {
            logger.info("多线程分片跑区间:" + (j * z + 1) + "-" + ((j + 1) * z));
            for (int i = j * z + 1; i < (j + 1) * z; i++) {
                HttpProxy httpProxy = proxyPool.borrow();
                HttpStatus code = ProxyIpCheck.Check(httpProxy.getProxy());
                logger.info("name:" + Thread.currentThread().getName() + httpProxy.getProxy() + ":" + code);

                proxyPool.reback(httpProxy,code); // 使用完成之后，归还 Proxy,并将请求结果的 http 状态码一起传入
            }
            latch.countDown();

            logger.info("当前线程" + Thread.currentThread().getName() + "执行完毕:");
        }
    }
}
