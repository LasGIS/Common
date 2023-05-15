package com.lasgis.reactive.repository;

import com.lasgis.reactive.ReactiveApplication;
import com.lasgis.reactive.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@SpringBootTest(classes = ReactiveApplication.class)
@AutoConfigureTestEntityManager
//@Transactional
class UserDtoRepositoryTest {

    private final UserDtoRepository repository;

    @Autowired
    public UserDtoRepositoryTest(UserDtoRepository repository) {
        this.repository = repository;
    }

    @Test
    void getAllUserDto() {
        final Flux<UserDto> flux = repository.getAllUserDto();
        final List<UserDto> list = flux.collectList().block();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
        log.info("list = {}", list);
    }
}