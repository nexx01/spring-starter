package shadow.dev.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.datatabase.querydsl.QPredicates;
import shadow.dev.spring.datatabase.repository.CompanyRepository;
import shadow.dev.spring.datatabase.repository.UserRepository;
import shadow.dev.spring.dto.dto.UserCreateEditDto;
import shadow.dev.spring.dto.dto.UserFilter;
import shadow.dev.spring.dto.dto.UserReadDto;
import shadow.dev.spring.mapper.UserCreateEditMapper;
import shadow.dev.spring.mapper.UserReadMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static shadow.dev.spring.datatabase.entity.QUser.user;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final CompanyRepository companyRepository;
    private final UserCreateEditMapper userCreateEditMapper;

    private final ImageService imageService;

//    @PostFilter("filterObject.role.name().equals('ADMIN')")
//    @PostFilter("@companyService.findById(filterObject.company.id().isPresent())")
    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.firsName(), user.firstName::containsIgnoreCase)
                .add(filter.lastName(), user.lastName::containsIgnoreCase)
                .add(filter.birthDate(), user.birthDate::before)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::map);
//        return userRepository.findAllByFilter(userFilter)
//                .stream().map(userReadMapper::map)
//                .toList();
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(dto->{
                    uploadImage(dto.getImage());
                    return userCreateEditMapper.map(dto);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userCreateEditDto) {
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(userCreateEditDto.getImage());
                    return userCreateEditMapper.map(userCreateEditDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }


    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                }).orElse(false);
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::isNotBlank)
                .flatMap(imageService::get);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user->new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(user.getRole())
                )).orElseThrow(()->new UsernameNotFoundException("Found to Retrive user: "+username));
    }
}
