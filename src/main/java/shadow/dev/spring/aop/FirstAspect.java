package shadow.dev.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
@Slf4j
@Order(1)
public class FirstAspect {

    /*
     * this - check AOP  proxy class type
     * target - check target object class type
     */
    @Pointcut("this(org.springframework.stereotype.Repository)")
    public void isRepositoryLayer() {
    }

    /*
     * @annotation  - check annotation on method level
     */
    @Pointcut("shadow.dev.spring.aop.CommonPoincut.isControllerLayer() && " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    /*
     * args -check method param type
     * * - any paarm type
     * .. - 0+ any param type
     */
    @Pointcut("shadow.dev.spring.aop.CommonPoincut.isControllerLayer() && args(org.springframework.ui.Model,..)")
//    @Pointcut("args(org.springframework.ui.Model,*,*)")
    public void hasModelParam() {
    }

    /*
     * args -check annotation on the param type
     * * - any paarm type
     * .. - 0+ any param type
     */
    @Pointcut("shadow.dev.spring.aop.CommonPoincut.isControllerLayer() && @args(shadow.dev.spring.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation() {
    }

    /*
     * bean - check bean name
     */
    @Pointcut("bean(*Service)")
    public void isServiceLayerBean() {
    }

    @Pointcut("execution(public * shadow.dev.spring.service.*Service.findById(*)) ")
    public void anyFindByIdServiceMethod() {
    }

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
