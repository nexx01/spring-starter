package shadow.dev.spring.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageRestController {

    private final MessageSource messageSource;

    @PostMapping
    public String getMessage(@RequestParam("key")String key,
                             @RequestParam("lang")String language) {
//        ResourceBundle.getBundle("messages").getString(key) // без спринга
        return messageSource.getMessage(key, null, null, new Locale(language));
    }
}
