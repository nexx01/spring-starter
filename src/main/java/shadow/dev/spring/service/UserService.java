package shadow.dev.spring.service;

import shadow.dev.spring.datatabase.repository.CompanyRepository;
import shadow.dev.spring.datatabase.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private CompanyService companyService;

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public UserService(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

}
