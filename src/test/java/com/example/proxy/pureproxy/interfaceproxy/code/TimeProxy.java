package com.example.proxy.pureproxy.interfaceproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy implements Logic{

    private final Logic target;

    public TimeProxy(Logic target){
        this.target = target;
    }

    @Override
    public String operation() {


        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        // 구체 클래스의 핵심 비즈니스 로직
        String operation = target.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료. resultTime = {}ms", resultTime);
        return operation;
    }
}
