package shadow.dev.spring.integration.datatabase.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import shadow.dev.spring.annotation.IT;
import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.datatabase.repository.UserRepository;
import shadow.dev.spring.dto.dto.PersonalInfo;
import shadow.dev.spring.dto.dto.UserFilter;
import shadow.dev.spring.integration.IntegrationTestBase;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

//@IT
@RequiredArgsConstructor
//@Transactional
class UserRepositoryTest  extends IntegrationTestBase {

    private final UserRepository userRepository;


    @Test
    void checkBatch() {
        var all = userRepository.findAll();
        userRepository.updateCompanyAndRole(all);
        System.out.println();
    }

    @Test
    void checkJdbcTemplate() {

        var allByCompanyIdAndRole = userRepository.findAllByCompanyIdAndRole(1, Role.USER);
        assertThat(allByCompanyIdAndRole).hasSize(1);
        System.out.println();
    }

    @Test
//    @Commit
    void checkAuditing() {
        var user = userRepository.findById(1L).get();
        user.setBirthDate(user.getBirthDate().minusYears(1));
        userRepository.flush();
        System.out.println();

    }

    @Test
    void checkCustomImplementationwithQueryDsl() {
        var userFilter = new UserFilter(null,"ov",LocalDate.now());
        var users = userRepository.findAllByFilter(userFilter);
        System.out.println();
    }

    @Test
    void checkCustomImplementation() {
        var userFilter = new UserFilter(null,"%ov%",LocalDate.now());
        var users = userRepository.findAllByFilter(userFilter);
        System.out.println();
    }

    @Test
    void checkProjection() {
        var users = userRepository.findAllByCompanyId(1);
        assertThat(users).hasSize(2);
        System.out.println();
    }

//    @Test
//    void checkProjection() {
//        var users = userRepository.findAllByCompanyId(1,PersonalInfo.class);
//        assertThat(users).hasSize(2);
//        System.out.println();
//    }

    @Test
    void checkPageableWithEntityGraph() {
        var pageRequest = PageRequest.of(1, 2, Sort.by("id"));
        var slice = userRepository.findAllBy(pageRequest);
        slice.forEach(user-> System.out.println(user.getCompany().getName()));

        while (slice.hasNext()){
            slice = userRepository.findAllBy(slice.nextPageable());
            slice.forEach(user-> System.out.println(user.getCompany().getName()));
        }
    }


    @Test
    void checkPageable() {
        var pageRequest = PageRequest.of(1, 2, Sort.by("id"));
        var slice = userRepository.findAllBy(pageRequest);
        slice.forEach(user-> System.out.println(user.getId()));

        while (slice.hasNext()){
             slice = userRepository.findAllBy(slice.nextPageable());
            slice.forEach(user-> System.out.println(user.getId()));
        }
    }


    String getHash(String word){
        var chars = word.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }
    @Test
    void checkSort() {
//        var sortById = Sort.by("firstName")
//                .and(Sort.by("lastName"));

        var sortBy = Sort.sort(User.class);
        sortBy.by(User::getFirstName).and(sortBy.by(User::getLastName));

        var allUsers = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), sortBy);
        assertThat(allUsers).hasSize(3);
    }

    @Test
    void checkFirstTop() {
//        var top3ByBirthDateBeforeOrderByBirthDateDesc = userRepository.findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate.now());
        var top3ByBirthDateBeforeOrderByBirthDateDesc = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), Sort.by("id").descending());
        assertThat(top3ByBirthDateBeforeOrderByBirthDateDesc).hasSize(3);
        var topUser = userRepository.findTopByOrderByIdDesc();

        assertTrue("",topUser.isPresent());
        topUser.ifPresent(user -> assertEquals("", 5L, user.getId()));
    }

    @Test
    void checkUpdate() {
        var ivan = userRepository.getById(1L);
        assertEquals("",Role.ADMIN, ivan.getRole());

        var resultCount = userRepository.update(Role.USER, 1L, 5L);
        assertEquals("",2, resultCount);

        ivan.getCompany().getName();

        var theSameIvan = userRepository.getById(1L);
        assertEquals("",Role.USER, ivan.getRole());
    }


    @Test
    void checkQueries() {

        var users = userRepository.findAllBy("a", "ov");
        assertThat(users).hasSize(3);
        System.out.println("------");
        System.out.println(users);
    }
}