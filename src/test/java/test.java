import com.myapp.timer.Timer;
import com.myapp.util.HttpStatus;
import com.myapp.util.ProxyIpCheck;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created by gaorui on 17/5/16.
 */
public class Test {

    private static Logger logger = Logger.getLogger(Test.class);
    @org.junit.Test
    public void TimerTest(){

        Timer timer = new Timer();
    }

    @org.junit.Test
    public void ProxyIpCheckTest(){
        String address = "54.169.241.114";
        int port = 80;
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(address, port));
        HttpStatus httpStatus = ProxyIpCheck.Check(proxy);
        Assert.assertEquals(HttpStatus.SC_OK,httpStatus);
    }

    @org.junit.Test
    public void logTest(){
        logger.error("s");
    }

}
