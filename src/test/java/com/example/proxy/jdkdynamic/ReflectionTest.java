package com.example.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){

        Hello target = new Hello();

        // 공통 로직 1 시작
        log.info("start");
        String result1 = target.callA(); // 호출하는 메서드가 다르다 : callA()
        log.info("result={}", result1);
        // 공통 로직 1 종료


        // 공통 로직 2 시작
        log.info("start");
        String result2 = target.callB();; // 호출하는 메서드가 다르다 : callB()
        log.info("result={}", result2);
        // 공통 로직 2 종료

    }

    @Test
    void refelction1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 정보 획득
        Class<?> classHello = Class.forName("com.example.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();


        // callA 의 메서드 정보 (문자열로 메서드 정보를 얻었다.)
        // 문자열로 메서드 정보를 얻었다는 것은 파라미터를 통해 얻을 수도 있다는 얘기가 된다.
        Method methodCallA = classHello.getMethod("callA");

        // 동적으로 얻은 메서드 정보를 이용해 메소드 call (invoke)
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);


        // callB 의 메서드 정보 (문자열로 메서드 정보를 얻었다.)
        Method methodCallB = classHello.getMethod("callB");

        // 동적으로 얻은 메서드 정보를 이용해 메소드 call (invoke)
        Object result2 = methodCallB.invoke(target);
        log.info("result1={}", result2);

    }

    @Test
    void refelction2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 정보 획득
        Class<?> classHello = Class.forName("com.example.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello{
        public String callA(){
            log.info("CallA");
            return "A";
        }
        public String callB(){
            log.info("CallB");
            return "B";
        }
    }

}
