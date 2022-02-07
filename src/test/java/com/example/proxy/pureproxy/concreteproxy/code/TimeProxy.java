package com.example.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic{

    private ConcreteLogic concreteLogic;

    public TimeProxy(ConcreteLogic concreteLogic){
        this.concreteLogic = concreteLogic;
    }

    // 구체 클래스의 핵심 비즈니스 로직을 수행하는 메소드를 Override 한다.
    @Override
    public String operation(){
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        // 구체 클래스의 핵심 비즈니스 로직
        String operation = concreteLogic.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료. resultTime = {}ms", resultTime);
        return operation;
    }
}
