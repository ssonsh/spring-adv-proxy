package com.example.proxy.trace.template;


import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    private final LogTrace logTrace;

    public AbstractTemplate(LogTrace trace){
        this.logTrace = trace;
    }

    public T execute(String message){
        TraceStatus status = null;
        try{
            status = logTrace.begin(message);

            // 핵심 기능 = 비즈니스 로직 호출
            T result = call();

            logTrace.end(status);
            return result;
        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
