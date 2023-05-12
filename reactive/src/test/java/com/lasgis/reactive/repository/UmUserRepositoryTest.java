package com.lasgis.reactive.repository;

import com.lasgis.reactive.ReactiveApplication;
import com.lasgis.reactive.entity.UmUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = ReactiveApplication.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional
class UmUserRepositoryTest {

    private final UmUserRepository repository;

    @Autowired
    public UmUserRepositoryTest(UmUserRepository repository) {
        this.repository = repository;
    }

    @Test
    void getAll() {
        Flux<UmUser> flux = repository.findAll();
        List<UmUser> list = flux.collectList().block();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
    }
}