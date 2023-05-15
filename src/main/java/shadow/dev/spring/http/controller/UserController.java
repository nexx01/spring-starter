package shadow.dev.spring.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.dto.dto.PageResponse;
import shadow.dev.spring.dto.dto.UserCreateEditDto;
import shadow.dev.spring.dto.dto.UserFilter;
import shadow.dev.spring.dto.dto.UserReadDto;
import shadow.dev.spring.service.CompanyService;
import shadow.dev.spring.service.UserService;
import shadow.dev.spring.validation.group.CreateAction;
import shadow.dev.spring.validation.group.UpdateAction;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.groups.Default;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final CompanyService  companyService;

    //    @ExceptionHandler({Exception.class})
//    public String handleExceptions(Exception exception,
//                                   HttpServletRequest request){
//      log.error("Failed to return response",exception);
//        return "error/error500";
//    }
//    @GetMapping(value = "/{id}/avatar",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public byte[] findAvatar(@PathVariable("id") Long id) {
//        return userService.findAvatar(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }




    @GetMapping
    public String findAll(Model model, UserFilter filter, Pageable pageable) {
//        model.addAttribute("users", userService.findAll());
        var all = userService.findAll(filter, pageable);

        model.addAttribute("users", PageResponse.of(all));
        model.addAttribute("filter", filter);
        return "user/users";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostAuthorize("returnObject")
    public String StringfindById(@PathVariable("id") Long id, Model model,
                                 @CurrentSecurityContext SecurityContext securityContext,
                                 @AuthenticationPrincipal UserDetails userDetails) {
     return    userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());

                    return "user/user";

                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute@Validated({Default.class, CreateAction.class}) UserCreateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        userService.create(user);
        return "redirect:/login" ;
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto userCreateEditDto) {
        model.addAttribute("user", userCreateEditDto);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "user/registration";
    }

    //    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated({Default.class, UpdateAction.class}) UserCreateEditDto user) {

        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
