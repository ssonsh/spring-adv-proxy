package com.example.proxy.pureproxy.interfaceproxy;

import com.example.proxy.pureproxy.interfaceproxy.code.InterfaceProxyClient;
import com.example.proxy.pureproxy.interfaceproxy.code.RealLogic;
import com.example.proxy.pureproxy.interfaceproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class InterfaceProxyTest {

    @Test
    void noProxy(){
        RealLogic realLogic = new RealLogic();
        InterfaceProxyClient client = new InterfaceProxyClient(realLogic);

        client.execute();
    }

    @Test
    void interfaceProxy(){
        RealLogic realLogic = new RealLogic();
        TimeProxy timeProxy = new TimeProxy(realLogic);
        InterfaceProxyClient client = new InterfaceProxyClient(timeProxy);

        client.execute();
    }
}
