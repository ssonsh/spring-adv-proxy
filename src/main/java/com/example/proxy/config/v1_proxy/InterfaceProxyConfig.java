package com.example.proxy.config.v1_proxy;

import com.example.proxy.app.v1.*;
import com.example.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import com.example.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import com.example.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import com.example.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    // TODO. 참고 : 기존 V1 App의 Configuration : AppV1Config

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace){

        // Proxy의 Target이 될 Controller 인터페이스 구현체 -> Service의 Proxy를 호출해야 한다.
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));

        // 만든 Proxy를 스프링 빈으로 반환한다.
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace){
        OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
    }

}
