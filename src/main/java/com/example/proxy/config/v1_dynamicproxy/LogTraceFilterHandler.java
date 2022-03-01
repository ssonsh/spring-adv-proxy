package com.example.proxy.config.v1_dynamicproxy;

import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // TODO. 메서드 이름 필터
        String methodName = method.getName();
        if(!PatternMatchUtils.simpleMatch(patterns, methodName)){
            // 실제 비즈니스 로직만 호출
            return method.invoke(target, args);
        }

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
