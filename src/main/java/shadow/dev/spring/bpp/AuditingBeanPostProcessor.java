package shadow.dev.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class AuditingBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> auditBeans = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Transaction.class)) {
            auditBeans.put(beanName, bean.getClass());
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = auditBeans.get(beanName);
        if (beanClass!=null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("Audit method: " + method.getName());
                    var startTIme = System.nanoTime();
                    try {
                        return method.invoke(bean, args);

                    } finally {

                        System.out.println("Time execution: " + (System.nanoTime() - startTIme));
                    }

                }
            });
        }
        return bean;
    }
}
