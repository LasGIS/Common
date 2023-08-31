package com.lasgis.prototype.git.admin.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * The Class UserUi definition.
 *
 * @author VLaskin
 * @since 27.06.2023 : 12:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUi {
    /**
     * имя учетной записи
     */
    private String username;
    /**
     * адрес электронной почты
     */
    private String email;
    /**
     * наличие роли «Администратор Решения»
     */
    private List<UserRole> roles;
    /**
     * статус блокировки учетной записи
     */
    private LockStatus status;
    /**
     * дата и время создания УЗ
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime createDt;
    /**
     * дата и время последнего входа в систему
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime lastDt;
}
