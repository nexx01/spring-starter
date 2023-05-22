package com.aadev.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Slf4j
public class FirstAspect {

    @Pointcut("execution(public * *.dev.spring.service.*Service.findById(*)) ")
    public void anyFindByIdServiceMethod() {
    }
//shadow.dev.spring.service.CompanyService
//    @Before("anyFindByIdServiceMethod()")
//    public void addLogging() {
//        log.info("invoke findById method");
//    }
//

    @Before(value = "anyFindByIdServiceMethod()" +
            "&& args(id) " +
            "&& target(service)" +
            "&& this(serviceProxy)" +
            "&& @within(transactional)", argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional
    ) {
    //    log.info("invoke findById method");
        log.info("before invoke findById method in class {}, whith id {}",service,id);
    }

    @AfterReturning(value = "anyFindByIdServiceMethod() && target(service)",
             returning = "result")
    public void addLoggingAfterReturninga(Object result, Object service) {
        log.info("after returning " +
                "findById method in class {}, whith id {}",service,result);

    }

    @AfterReturning(value = "anyFindByIdServiceMethod() && target(service)",
            returning = "ex")
    public void addLoggingAfterThrowing(Throwable ex, Object service) {
        log.info("after throwing " +
                "findById method in class {}, exeption {}: {}", service, ex.getClass(), ex.getMessage());

    }

    @After( "anyFindByIdServiceMethod() && target(service)")
    public void addLoggingAfterReturninga(Object service) {
        log.info("after (finnaly) " +
                "findById method in class {}", service);
    }


}
