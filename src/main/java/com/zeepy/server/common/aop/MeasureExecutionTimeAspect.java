package com.zeepy.server.common.aop;

/**
 * Created by Minky on 2021-08-26
 */

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * 함수 시간 측정 AOP
 * 성능 테스트에 사용
 */

@Aspect
@Component
public class MeasureExecutionTimeAspect {
    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("execution(* com.zeepy.server..service.*Service.*(..))\n")
    public Object measureService(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start();

        Object result = joinPoint.proceed();

        // after advice
        sw.stop();
        Long total = sw.getTotalTimeMillis();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String taskName = className + "." + methodName;

        System.out.println("[ExecutionTime] " + taskName + " , " + total + "(ms)");

        logger.info("Execute : { package : " + joinPoint.getSignature().getDeclaringTypeName() + " }, method : "
                + joinPoint.getSignature().getName() + " }");
        return result;
    }

    @Around("execution(* com.zeepy.server..repository.*Repository.*(..))\n")
    public Object measureRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        // before advice
        StopWatch sw = new StopWatch();
        sw.start();

        Object result = joinPoint.proceed();

        // after advice
        sw.stop();
        Long total = sw.getTotalTimeMillis();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String taskName = className + "." + methodName;

        System.out.println("[ExecutionTime] " + taskName + " , " + total + "(ms)");

        logger.info("Execute : { package : " + joinPoint.getSignature().getDeclaringTypeName() + " }, method : "
                + joinPoint.getSignature().getName() + " }");
        return result;
    }
}
