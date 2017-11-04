package com.guild.api.demo.util.hystrix;

import static com.netflix.hystrix.HystrixCommand.Setter.withGroupKey;
import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;
import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.THREAD;

import java.util.concurrent.Callable;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class HystrixExecutor {
    private static final int DEFAULT_POOL_SIZE = 20;
    private static final int DEFAULT_TIMEOUT = 10000;

    private String groupName;
    private String poolName;
    private int coreSize = DEFAULT_POOL_SIZE;
    private int timeout = DEFAULT_TIMEOUT;

    public HystrixExecutor(String groupName) {
        this.groupName = groupName;
        this.poolName = groupName;
    }

    public HystrixExecutor withThreadPool(String poolName, int coreSize) {
        this.poolName = poolName;
        this.coreSize = coreSize;
        return this;
    }

    public HystrixExecutor withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public <T> T execute(String commandName, final Callable<T> command) {
        return new HystrixCommand<T>(withGroupKey(asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandName))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(poolName))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(coreSize))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(THREAD)
                        .withExecutionTimeoutInMilliseconds(timeout)
                        .withCircuitBreakerForceClosed(true))
        ) {
            @Override
            protected T run() throws Exception {
                return command.call();
            }
        }.execute();
    }
}
