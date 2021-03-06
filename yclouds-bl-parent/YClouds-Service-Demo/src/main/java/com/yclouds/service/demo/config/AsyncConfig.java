package com.yclouds.service.demo.config;

import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author ye17186
 * @version 2018/8/21 18:31
 */
@Slf4j
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 线程执行器配置
     *
     * @return 线程Executor
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 线程池核心大小
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(50);
        // 线程名前缀
        executor.setThreadNamePrefix("AsyncTask-");
        executor.initialize();
        return executor;
    }

    /**
     * 异常处理器
     * <br>异步方法返回类型为void时，会走此异常处理
     * <br>而返回类型为Future时，在get结果时，会抛出ExecutionException异常
     *
     * @see org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> log
            .error("Unexpected exception occurred invoking async method : {}", method, throwable);
    }
}
