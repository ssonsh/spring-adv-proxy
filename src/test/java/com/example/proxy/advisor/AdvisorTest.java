package com.example.proxy.advisor;

import com.example.proxy.common.advice.TimeAdvice;
import com.example.proxy.common.service.ServiceImpl;
import com.example.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest1(){

        // 인터페이스
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 어드바이저 생성
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

        // 프록시 팩토리에 어드바이저 등록
        proxyFactory.addAdvisor(advisor);

        // 프록시 팩토리에서 프록시 객체 획득
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());

        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointcut implements Pointcut {

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }

        static class MyMethodMatcher implements MethodMatcher {

            // save() 메소드는 포인트컷을 통해 어드바이스 로직을 적용할 것이고
            // save() 메소드 외에는 적용하지 않을 것이다.
            private String matchName = "save";

            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                boolean result = method.getName().equals(matchName);
                log.info("포인트컷 호출 method = {}, targetClass = {}", method.getName(), targetClass);
                log.info("포인트컷 결과 result = {}", result);
                return result;
            }

            @Override
            public boolean isRuntime() {
                return false;
            }

            @Override
            public boolean matches(Method method, Class<?> targetClass, Object... args) {
                throw new UnsupportedOperationException();
            }
        }
    }



    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Spring이 제공하는 NameMatchMethodPointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());

        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

}
