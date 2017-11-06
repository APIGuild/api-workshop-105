package com.guild.api.demo.util.logging;

import static java.lang.String.format;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class PerformanceLogInterceptor {
    private final Logger LOGGER = LoggerFactory.getLogger(PerformanceLog.class);

    @Value("${spring.application.name}")
    private String applicationName;

    @Around("@annotation(com.guild.api.demo.util.logging.PerformanceLog)")
    public Object logPerformance(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            Object result = proceedingJoinPoint.proceed();
            stopWatch.stop();
            logResult("success", proceedingJoinPoint, stopWatch);
            return result;
        } catch (Throwable throwable) {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
            logResult("error", proceedingJoinPoint, stopWatch);
            throw throwable;
        }
    }

    private void logResult(String result, ProceedingJoinPoint proceedingJoinPoint, StopWatch stopWatch) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String serviceName = signature.getDeclaringType().getSimpleName();
        String operationName = signature.getName();
        LOGGER.info(format("============>> %1$s|%2$s|%3$s|%4$s|%5$s ms", applicationName, serviceName, operationName, result, stopWatch.getTotalTimeMillis()));
    }
}
