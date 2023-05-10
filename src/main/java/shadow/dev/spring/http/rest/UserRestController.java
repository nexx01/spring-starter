package shadow.dev.spring.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import shadow.dev.spring.dto.dto.PageResponse;
import shadow.dev.spring.dto.dto.UserCreateEditDto;
import shadow.dev.spring.dto.dto.UserFilter;
import shadow.dev.spring.dto.dto.UserReadDto;
import shadow.dev.spring.service.UserService;
import shadow.dev.spring.validation.group.CreateAction;
import shadow.dev.spring.validation.group.UpdateAction;

import javax.validation.groups.Default;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {
    private final UserService userService;


    @GetMapping
    @ResponseBody
    public PageResponse<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        var page = userService.findAll(filter, pageable);

        return PageResponse.of(page);
    }



    @GetMapping("/{id}")
    public UserReadDto StringfindById(@PathVariable("id") Long id, Model model) {
        return    userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@ModelAttribute@Validated({Default.class, CreateAction.class}) @RequestBody UserCreateEditDto user) {
        return userService.create(user);
    }


    @PutMapping("/{id}/update")
    public UserReadDto update(@PathVariable("id") Long id,
                              @Validated({Default.class, UpdateAction.class})
                         @RequestBody UserCreateEditDto user) {

        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/avatar",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id) {
        return userService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content)
                ).orElseGet(ResponseEntity.notFound()::build);
    }

//        @DeleteMapping("/{id}")
//        @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable("id") Long id) {
//        if (!userService.delete(id)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return userService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.noContent().build();
    }

}
