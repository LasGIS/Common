/*
 *  @(#)UserDtoResultSetExtractor.java  last: 13.06.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.repository.extractor;

import com.lasgis.prototype.vue.model.UserDto;
import com.lasgis.prototype.vue.model.UserRole;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * The Class UserDtoResultSetExtractor definition.
 *
 * @author Vladimir Laskin
 * @since 01.05.2023 : 20:37
 */
public class UserDtoResultSetExtractor implements ResultSetExtractor<List<UserDto>> {

    public static final UserDtoResultSetExtractor INSTANCE = new UserDtoResultSetExtractor();

    @Override
    public List<UserDto> extractData(final ResultSet rs) throws SQLException, DataAccessException {
        final List<UserDto> list = new ArrayList<>();
        Integer userId = null;
        UserDto.UserDtoBuilder builder = null;
        List<UserRole> roles = new ArrayList<>();
        while (rs.next()) {
            final Integer rsUserId = rs.getInt("umusr_user_id");
            if (!rsUserId.equals(userId)) {
                if (nonNull(builder)) {
                    list.add(builder.roles(roles).build());
                    roles = new ArrayList<>();
                }
                builder = UserDto.builder().userId(rsUserId)
                    .login(rs.getString("umusr_login"))
                    .name(rs.getString("umusr_name"))
                    .password(rs.getString("umusr_password"))
                    .archived(rs.getBoolean("umusr_archived"));
                userId = rsUserId;
            }
            final String roleId = rs.getString("umrle_role_id");
            if (nonNull(roleId)) {
                roles.add(UserRole.valueOf(roleId));
            }
        }
        if (nonNull(builder)) {
            list.add(builder.roles(roles).build());
        }
        return list;
    }
}
