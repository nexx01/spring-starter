package com.aadev.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
public class SecondAspect {
    @Around("com.aadev.logging.aop.FirstAspect.anyFindByIdServiceMethod() && target(service) && args(id)")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service, Object id) throws Throwable {
        log.info("AROUND before invoke findById method in class {}, whith id {}",service,id);
        try {
            var result = joinPoint.proceed();
            log.info("AROUND after returning findById method in class {}, whith id {}", service, id);
            return result;
        } catch (Exception ex) {
            log.info("AROUND after throwing " +
                    "findById method in class {}, exeption {}: {}", service, ex.getClass(), ex.getMessage());
            throw ex;
        }finally {
            log.info("AROUND after (finnaly) " +
                    "findById method in class {}", service);
        }


    }

}
