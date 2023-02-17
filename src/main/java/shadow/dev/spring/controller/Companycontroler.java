package shadow.dev.spring.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;
import shadow.dev.spring.datatabase.entity.Company;

@RestController
@RequestMapping("/company")
public class Companycontroler {

    @PostMapping
    public Company setCompany(@RequestBody Company company) {
//        System.out.println("headers: " + headers);
        System.out.println("\nCompany: " + company);
        return company;
    }
}
