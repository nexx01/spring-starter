package shadow.dev.spring.http.controller;

import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.dto.dto.UserReadDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("api/v1")
@SessionAttributes({"user"})
public class GreetingController {

//    @GetMapping(value = "/hello")
//    public ModelAndView hello(ModelAndView modelAndView,
//                              HttpServletRequest request) {
////        request.setAttribute();
////        request.getSession().setAttribute();
//        modelAndView.setViewName("greeting/hello");
//        modelAndView.addObject("user", new UserReadDto(1L,"Ivan"));
//        return modelAndView;
//    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping(value = "/hello")
    public String hello(Model model,
                              HttpServletRequest request
            ,@ModelAttribute("userReadDto") UserReadDto userReadDto) {
        model.addAttribute("user", userReadDto);
//        model.addAttribute("user", new UserReadDto(1L,"Ivan"));
        return "greeting/hello";
    }

    @GetMapping(value = "/bye")

    public ModelAndView bye(@SessionAttribute("user")UserReadDto user,
                                ModelAndView modelAndView) {
//        var modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting/bye");

        return modelAndView;
    }

    @GetMapping(value = "/bye2")

    public String bye2(@SessionAttribute("user")UserReadDto user,
                            ModelAndView modelAndView) {
//        var modelAndView = new ModelAndView();
//        modelAndView.setViewName("greeting/bye");
        return "greeting/bye";
//        return modelAndView;
    }
    @GetMapping(value = "/hello/{id}")
    public ModelAndView hello2(ModelAndView modelAndView,
                              HttpServletRequest request,
                              @RequestParam(value = "age",required = false) Integer age,
                              @RequestHeader("accept")String accept,
                              @CookieValue("JSESSIONID")String jsessionId,
                              @PathVariable("id")Integer id) {
//        var modelAndView = new ModelAndView();
        var ageParamValue = request.getParameter("age");
        request.getHeader("accept");
        Cookie[] cookies = request.getCookies();
        modelAndView.setViewName("greeting/hello");
        var chars = Character.toChars(1);
        return modelAndView;
    }
}
