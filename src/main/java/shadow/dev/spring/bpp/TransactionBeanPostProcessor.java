package shadow.dev.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
@Component

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> transactionalBeam = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Transaction.class)) {
            transactionalBeam.put(beanName, bean.getClass());
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = transactionalBeam.get(beanName);
        if (beanClass!=null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("Open transaction");
                    try {
                        return method.invoke(bean, args);
                    }catch (Exception exception){
                        System.out.println("Rollback transaction");
                        throw exception;
                    } finally {

                        System.out.println("Close transaction");
                    }

                }
            });
        }
        return bean;
    }
}
