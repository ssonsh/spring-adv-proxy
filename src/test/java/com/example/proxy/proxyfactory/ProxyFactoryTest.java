package com.example.proxy.proxyfactory;

import com.example.proxy.common.advice.TimeAdvice;
import com.example.proxy.common.service.ConcreteService;
import com.example.proxy.common.service.ServiceImpl;
import com.example.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy(){
        ServiceInterface target = new ServiceImpl();

        // ProxyFactory 생성 시 인자로 Target 을 전달받는다.
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // ProxyFactory에 생성한 Advice를 add 해준다.
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        // Proxy Factory를 생성해서 만든 Proxy인 경우에만 AopUtils를 통해 확인할 수 있다.
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }


    @Test
    @DisplayName("구체 클래스가 있으면 CGLIB 사용")
    void concreateProxy(){
        ConcreteService target = new ConcreteService();

        // ProxyFactory 생성 시 인자로 Target 을 전달받는다.
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // ProxyFactory에 생성한 Advice를 add 해준다.
        proxyFactory.addAdvice(new TimeAdvice());

        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

        // Proxy Factory를 생성해서 만든 Proxy인 경우에만 AopUtils를 통해 확인할 수 있다.
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }


    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass(){
        ServiceInterface target = new ServiceImpl();

        // ProxyFactory 생성 시 인자로 Target 을 전달받는다.
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Proxy를 TargetClass 기반으로 만들겠다.
        proxyFactory.setProxyTargetClass(true);

        // ProxyFactory에 생성한 Advice를 add 해준다.
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        // Proxy Factory를 생성해서 만든 Proxy인 경우에만 AopUtils를 통해 확인할 수 있다.
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
