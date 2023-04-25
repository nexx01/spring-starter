package shadow.dev.spring.datatabase.repository;

import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.dto.dto.PersonalInfo;
import shadow.dev.spring.dto.dto.PersonalInfo2;
import shadow.dev.spring.dto.dto.UserFilter;

import java.util.List;

public interface FilterUserRepository {
    List<User> findAllByFilter(UserFilter filter);
    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);
    void updateCompanyAndRole(List<User> users);
    void updateCompanyAndRoleNamed(List<User> users);
}
