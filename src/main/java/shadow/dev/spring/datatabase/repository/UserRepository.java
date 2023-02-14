package shadow.dev.spring.datatabase.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import shadow.dev.spring.datatabase.pool.ConnectionPool;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class UserRepository {


    @Qualifier("pool2")
    private final ConnectionPool connectionPool;

}
