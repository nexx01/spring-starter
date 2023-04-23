package shadow.dev.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.repository.CompanyRepository;
import shadow.dev.spring.datatabase.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

}
