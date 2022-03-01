package com.example.proxy.config.v1_dynamicproxy;

import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceBasicHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;

    public LogTraceBasicHandler(Object target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TraceStatus status = null;
        try{
            // AS-IS
            // status = logTrace.begin("OrderController.request()");

            // TO-BE
            String logMessage = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(logMessage);

            // target 호출
            // AS-IS
            // String result = target.request(itemId);

            // TO-BE
            Object result = method.invoke(target, args);
            logTrace.end(status);

            return result;
        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
