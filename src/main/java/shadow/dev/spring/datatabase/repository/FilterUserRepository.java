package shadow.dev.spring.datatabase.repository;

import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.dto.dto.UserFilter;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);
}
