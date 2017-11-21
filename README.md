### ProxyIpPool运行部署说明
> * src\main\resources\redis.properties 对redis进行配置
> * 运行测试:com.myapp.timer.Timer   main函数
> * Maven 打包部署
target目录下的lib目录与ProxyIpPool.jar包一起入到同一目录下,执行:
```bash
$ nohup java -jar ProxyIpPool.jar &
```

[![Build Status](https://travis-ci.org/javagaorui5944/ProxyIpPool.svg?branch=master)](https://travis-ci.org/javagaorui5944/ProxyIpPool)

### 3个定时器线程,第一个负责收集代理ip,第二个个负责运行维护代理ip,第三个负责存储到redis,(redis并做持久化服务),并于每天提供服务后重新放入池子第二天再进行维护。
#### qq技术交流群:200255678
#### 系统原理图

![cmd-markdown-logo](http://o9beglkd1.bkt.clouddn.com/proxyippool.png)
