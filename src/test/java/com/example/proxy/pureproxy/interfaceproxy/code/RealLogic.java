package com.example.proxy.pureproxy.interfaceproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealLogic implements Logic{
    @Override
    public String operation() {
        log.info("RealLogic 실행!");
        return "data";
    }
}
