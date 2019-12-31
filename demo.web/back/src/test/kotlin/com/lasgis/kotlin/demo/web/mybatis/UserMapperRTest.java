package com.lasgis.kotlin.demo.web.mybatis;

import com.lasgis.kotlin.demo.web.dao.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>31.12.2019</pre>
 */
@ExtendWith(SpringExtension.class)
@MybatisTest
public class UserMapperRTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findByLoginTest() {
        User user = userMapper.findByLogin("lasgis");
        Assertions.assertNotNull(user);
            assertThat(user.getUserId()).isEqualTo(1);
            assertThat(user.getLogin()).isEqualTo("LasGIS");
            assertThat(user.getName()).isEqualTo("San Francisco");
            assertThat(user.getArchived()).isFalse();
    }

}