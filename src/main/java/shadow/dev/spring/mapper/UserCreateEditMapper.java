package shadow.dev.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.datatabase.repository.CompanyRepository;
import shadow.dev.spring.dto.dto.UserCreateEditDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {
    private final CompanyRepository companyRepository;


    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        var user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setUsername(object.getUserName());
        user.setFirstName(object.getFirstName());
        user.setLastName(object.getLastName());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompanyId()));

        Optional.ofNullable(object.getImage())
                .filter(MultipartFile::isEmpty)
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }

    public Company getCompany(Long companyId) {
        return Optional.ofNullable(companyId)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }


}
