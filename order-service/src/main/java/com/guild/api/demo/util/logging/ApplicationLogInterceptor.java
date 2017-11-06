package com.guild.api.demo.util.logging;

import static java.lang.String.format;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.common.base.Joiner;

@Aspect
@Component
public class ApplicationLogInterceptor {
    @Before("@annotation(com.guild.api.demo.util.logging.ApplicationLog)")
    public void logInvocation(JoinPoint joinPoint) {
        getLogger(joinPoint).info(format("============>> Invoking %s(%s)", getMethod(joinPoint).getName(), getArguments(joinPoint)));
    }

    @AfterReturning(pointcut = "@annotation(com.guild.api.demo.util.logging.ApplicationLog)", returning = "returning")
    public void logReturning(JoinPoint joinPoint, Object returning) {
        getLogger(joinPoint).info(format("============>> Completed %s, returned %s", getMethod(joinPoint).getName(), returning));
    }

    @AfterThrowing(pointcut = "@annotation(com.guild.api.demo.util.logging.ApplicationLog)", throwing = "exception")
    public void logExceptions(JoinPoint joinPoint, Throwable exception) throws Throwable {
        getLogger(joinPoint).error(exception.getMessage(), exception);
        throw exception;
    }

    private String getArguments(JoinPoint joinPoint) {
        return Joiner.on(",").useForNull("null").join(joinPoint.getArgs());
    }

    private Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    }

    private Method getMethod(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }
}
