package com.lasgis.prototype.git.admin.model.user;

import lombok.Getter;

/**
 * The Class Locked definition.
 *
 * @author Vladimir Laskin
 * @since 30.04.2023 : 22:01
 */
public enum LockStatus {
    LOCKED("Заблокирован"), UNLOCKED("Не заблокирован");
    @Getter
    private final String definition;

    LockStatus(String definition) {
        this.definition = definition;
    }
}
