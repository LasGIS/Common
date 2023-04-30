package com.lasgis.prototype.vue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Class User definition.
 *
 * @author Vladimir Laskin
 * @since 30.04.2023 : 21:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {
    private Integer userId;
    private String login;
    private String name;
    private String password;
    private List<UserRole> roles;
    private Boolean archived;
}
