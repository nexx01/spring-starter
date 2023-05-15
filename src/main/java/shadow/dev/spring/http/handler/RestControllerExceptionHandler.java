package shadow.dev.spring.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice(basePackages = "shadow.dev.spring.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({Exception.class})
//    public String handleExceptions(Exception exception,
//                                   HttpServletRequest request){
//        log.error("Failed to return response",exception);
//        return "error/error500";
//    }
}
