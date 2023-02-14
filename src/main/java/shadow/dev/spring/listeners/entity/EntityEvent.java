package shadow.dev.spring.listeners.entity;

import lombok.Getter;

import java.util.EventObject;


public class EntityEvent extends EventObject {
    @Getter
    private final AccessType accessType;

    public EntityEvent(Object entity,AccessType accessType) {
        super(entity);
        this.accessType = accessType;
    }
}
