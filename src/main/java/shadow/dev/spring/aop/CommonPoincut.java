package shadow.dev.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
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
    @Pointcut("within(shadow.dev.spring.service.*Service)")
    public void isServiceLayer() {
    }


}
