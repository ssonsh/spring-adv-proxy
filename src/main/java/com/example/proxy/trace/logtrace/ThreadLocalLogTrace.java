package com.example.proxy.trace.logtrace;

import com.example.proxy.trace.TraceId;
import com.example.proxy.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {

        syncTraceId();

        // ThreadLocal에서 get() 하여 가져온다.
        TraceId traceId = traceIdHolder.get();

        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {

        // ThreadLocal에서 get 하여 TraceId를 가져온다.
        TraceId traceId = traceIdHolder.get();

        // ThreadLocal에 set 하여 TraceId를 넣어준다.
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        }
        else {
            traceIdHolder.set(traceId.createNextId());
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                     addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
                     resultTimeMs);
        }
        else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                     addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
                     e.toString());
        }

        releaseTraceId();
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) {

            // 해당 ThreadLocal이 저장하고 있떤 데이터를 제거한다.
            // 모든 데이터가 제거되는 것이 아니라 특정 Thread가 처리한 값을 관리하는 ThreadLocal의 데이터를 제거하는 것이다.
            traceIdHolder.remove();
        }
        else {
            traceIdHolder.set(traceId.createPreviousId());
        }
    }

    private Object addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }
}
