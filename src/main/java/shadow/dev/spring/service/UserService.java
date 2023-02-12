package shadow.dev.spring.service;

import org.springframework.stereotype.Service;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.repository.CompanyRepository;
import shadow.dev.spring.datatabase.repository.CrudRepository;
import shadow.dev.spring.datatabase.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;

    public UserService(UserRepository userRepository, CrudRepository<Integer, Company> companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }
}
