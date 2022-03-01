package com.example.proxy.config.v1_proxy.concrete_proxy;

import com.example.proxy.app.v2.OrderServiceV2;
import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target,
                                     LogTrace logTrace) {
        // 부모 클래스의 생성자를 사용하지 않을 것임으로 null로 할당
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try{
            status = logTrace.begin("OrderService.orderItem()");

            // target 호출
            target.orderItem(itemId);
            logTrace.end(status);

        }catch(Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
