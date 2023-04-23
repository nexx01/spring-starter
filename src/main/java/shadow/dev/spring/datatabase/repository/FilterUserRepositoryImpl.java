package shadow.dev.spring.datatabase.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
//import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.datatabase.entity.QUser;
import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.datatabase.querydsl.QPredicates;
import shadow.dev.spring.dto.dto.UserFilter;

import javax.persistence.EntityManager;
import java.util.List;

import static shadow.dev.spring.datatabase.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository{

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.firsName(), user.firstName::containsIgnoreCase)
                .add(filter.lastName(), user.lastName::containsIgnoreCase)
                .add(filter.birthDate(), user.birthDate::before)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
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
