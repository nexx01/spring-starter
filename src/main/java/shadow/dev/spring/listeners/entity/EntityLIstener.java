package shadow.dev.spring.listeners.entity;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class EntityLIstener {

//    @EventListener(condition = "#root.args[0].accessType.name()=='DELETE'")
    @EventListener(condition = "#p0.accessType.name()=='READ'")
    @Order(10)
    public void acceptEntityRead(EntityEvent entityEvent) {
        System.out.println("Entity: " + entityEvent);
    }
}
