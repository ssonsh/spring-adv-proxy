package com.example.proxy.pureproxy.interfaceproxy.code;

public class InterfaceProxyClient {

    private final Logic logic;

    public InterfaceProxyClient(Logic logic){
        this.logic = logic;
    }

    public void execute(){
        logic.operation();
    }
}
