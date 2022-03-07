package com.example.proxy;

import com.example.proxy.config.AopConfig;
import com.example.proxy.config.v4_postprocessor.BeanPostProcessorConfig;
import com.example.proxy.config.v5_autoproxy.AutoProxyConfig;
import com.example.proxy.trace.logtrace.LogTrace;
import com.example.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.aop.framework.AopProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

// @Import(BeanPostProcessorConfig.class)
// @Import(AutoProxyConfig.class)
@Import(AopConfig.class)
@SpringBootApplication(scanBasePackages = "com.example.proxy.app")
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

    @Bean
    public LogTrace logTrace(){
        return new ThreadLocalLogTrace();
    }
}
