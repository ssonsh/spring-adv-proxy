package com.example.proxy.cglib;

import com.example.proxy.cglib.code.TimeMethodInterceptor;
import com.example.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib(){

        // 인터페이스가 없는 구체클래스 : ConcreteService
        ConcreteService target = new ConcreteService();

        // 구체 클래스를 기반으로 프록시를 만든다.
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor((target)));

        ConcreteService proxy = (ConcreteService) enhancer.create();

        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.call();
    }
}
