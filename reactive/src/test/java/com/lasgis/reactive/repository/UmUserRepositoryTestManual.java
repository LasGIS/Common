package com.lasgis.reactive.repository;

import com.lasgis.reactive.ReactiveApplication;
import com.lasgis.reactive.entity.UmUser;
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
class UmUserRepositoryTestManual {

    private final UmUserRepository repository;

    @Autowired
    public UmUserRepositoryTestManual(UmUserRepository repository) {
        this.repository = repository;
    }

    @Test
    void getAllUmUser() {
        Flux<UmUser> flux = repository.findAll();
        List<UmUser> list = flux.collectList().block();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
        log.info("list = {}", list);
    }
}