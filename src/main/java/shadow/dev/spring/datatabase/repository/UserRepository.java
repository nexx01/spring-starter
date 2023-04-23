package shadow.dev.spring.datatabase.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.datatabase.pool.ConnectionPool;
import shadow.dev.spring.dto.dto.PersonalInfo;
import shadow.dev.spring.dto.dto.PersonalInfo2;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        RevisionRepository<User,Long,Integer>,
        QuerydslPredicateExecutor<User> {

    @Query("select u from User u" +
            " where u.firstName like %:firstName% and u.lastName like %:lastName%")
    List<User> findAllBy(String firstName, String lastName);


    @Query(value = "SELECT u.* FROM users u " +
            "WHERE u.username = :username ",nativeQuery = true)
    List<User> findAllByUsername(String username);

    @Query("update User u set u.role = :role " +
            "where u.id in (:ids)")
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    int update(Role role, Long... ids);

    Optional<User> findTopByOrderByIdDesc();

    List<User> findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate birthDate);


    @QueryHints(@QueryHint(name="org.hibernate.fetchSize",value="50"))
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

//    Slice<User> findAllBy(Pageable pageable);


//    @Query(value="select u from User u",
//    countQuery ="select count(distinct u.firstName) from User u")
//    Page<User> findAllBy(Pageable pageable);


//    @EntityGraph("User.company")
    @EntityGraph(attributePaths = {"company","company.locales"})
    @Query(value="select u from User u",
            countQuery ="select count(distinct u.firstName) from User u")
    Page<User> findAllBy(Pageable pageable);

//    List<PersonalInfo> findAllByCompanyId(Integer companyId);
//    <T> List<T> findAllByCompanyId(Integer companyId,Class<T> clazz);

    @Query(nativeQuery = true,
            value = "SELECT firstName," +
                    " lastName, " +
                    "birth_date as birthDate " +
                    "FROM users WHERE company_id = :companyId")
    List<PersonalInfo2> findAllByCompanyId(Integer companyId);

}
