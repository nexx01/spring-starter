package shadow.dev.spring.datatabase.repository;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
//import shadow.dev.spring.datatabase.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.datatabase.querydsl.QPredicates;
import shadow.dev.spring.dto.dto.PersonalInfo;
import shadow.dev.spring.dto.dto.UserFilter;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static shadow.dev.spring.datatabase.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository{

    String FIND_BY_COMPANY_AND_ROLE = """
                SELECT
                   firstname,
                   lastname,
                   birth_date
                FROM users
                WHERE company_id = ?
                AND role =?
            """;

    private static final String UPATE_COMPANY_AND_ROLE = """
                   UPDATE users
                   SET company_id=?,
                   role=?
                   WHERE id=?

            """;

    private static final String UPATE_COMPANY_AND_ROLE_NAMED = """
                   UPDATE users
                   SET company_id=:companyId,
                   role=:role
                   WHERE id=:id
            """;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        var predicate = QPredicates.builder()
//                .add(filter.firsName(), user.firstName::containsIgnoreCase)
//                .add(filter.lastName(), user.lastName::containsIgnoreCase)
//                .add(filter.birthDate(), user.birthDate::before)
                .build();

        return new JPAQuery<User>(entityManager)
//                .select(user)
//                .from(user)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {

        return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE,
                (rs, rowNum) -> new PersonalInfo(
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getDate("birth_date").toLocalDate()
        ), companyId,role.name());
    }

    @Override
    public void updateCompanyAndRole(List<User> users) {
        var parametrs = users.stream().map(user -> new Object[]{user.getCompany().getId(),
                        user.getRole().name(), user.getId()})
                .toList();
        jdbcTemplate.batchUpdate(UPATE_COMPANY_AND_ROLE, parametrs);
    }

    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        var mapSqlParameterSources = users.stream()
                .map(user -> Map.of(
                        "companyId", user.getCompany().getId(),
                        "role", user.getRole().name(),
                        "id", user.getId()
                )).map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(UPATE_COMPANY_AND_ROLE_NAMED, mapSqlParameterSources);
    }

//    @Override
//    public List<User> findAllByFilter(UserFilter filter) {
//        var cb = entityManager.getCriteriaBuilder();
//        var criteria = cb.createQuery(User.class);
//
//        var user = criteria.from(User.class);
//        criteria.select(user);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (filter.firsName() != null) {
//            predicates.add(cb.like(user.get("firstName"),filter.firsName()));
//        }
//
//        if (filter.lastName() != null) {
//            predicates.add(cb.like(user.get("lastName"),filter.lastName()));
//        }
//
//        if (filter.birthDate() != null) {
//            predicates.add(cb.lessThan(user.get("birthDate"),filter.birthDate()));
//        }
//
//        criteria.where(predicates.toArray(Predicate[]::new));
//        return entityManager.createQuery(criteria).getResultList();
//    }

}
