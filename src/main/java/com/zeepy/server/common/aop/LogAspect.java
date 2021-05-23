package com.zeepy.server.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Minky on 2021-05-23
 */
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("execution(* com.zeepy.server..controller.*Controller.*(..))\n || execution(* com.zeepy.server..service.*Service.*(..))\n")
    public Object infoLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        logger.info("Execute : { package : " + joinPoint.getSignature().getDeclaringTypeName() + " }, method : " + joinPoint.getSignature().getName() + " }");
        return result;
    }

    @Around("execution(* com.zeepy.server..CustomExceptionHandler.*.*(..))\n")
    public Object exceptionLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        logger.error("Exception : { package : " + joinPoint.getSignature().getDeclaringTypeName() + " }, method : " + joinPoint.getSignature().getName() + " }");
        return result;
    }

}
