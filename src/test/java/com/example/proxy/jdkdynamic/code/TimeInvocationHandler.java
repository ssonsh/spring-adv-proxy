package com.example.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    // Proxy는 항상 Proxy가 호출할 대상이 있어야 한다.
    // 테스트용이고 범용으로 처리할 수 있기 때문에 Object로 선언한다.
    private final Object target;

    public TimeInvocationHandler(Object target) {this.target = target;}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = method.invoke(target, args);

        long endTime = System.currentTimeMillis();
        long resultTime = endTime = startTime;

        log.info("TImeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
