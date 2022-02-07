package com.example.proxy.pureproxy.decorator.code;

public abstract class Decorator implements Component{
    private Component component;

    public Decorator(Component component){
        this.component = component;
    }
}
