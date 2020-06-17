package com.lphu.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author: lphu
 * @create: 2019-01-09 19:45
 * @description:
 */

@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Pointcut("execution(* com.lphu.controller..*.*(..))")
    public void controller(){}

    @Around("controller()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        String invoke = pjp.getTarget().getClass().getSimpleName() + "#" + pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        Optional<String> optional = Stream.of(args).map(ojbect -> ", {}")
                .reduce((accumulator, currentValue) -> accumulator += currentValue);
        log.info(invoke + "(" + optional.orElse("  ").substring(2) + ")", args);
        Object object = pjp.proceed();
        log.info(invoke + " return {}", object);
        return object;
    }
}
