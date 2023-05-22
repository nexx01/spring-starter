package com.aadev.logging.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
public class CommonPoincut {
    /*
     * @whitin - check annotation on the class level
     */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    /*
     * whithin - check class type name
     */
    @Pointcut("within(*.dev.spring.service.service.*Service)")
    public void isServiceLayer() {
    }


}
