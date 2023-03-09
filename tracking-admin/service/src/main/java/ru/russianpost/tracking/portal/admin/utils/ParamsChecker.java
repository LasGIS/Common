/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.utils;

import lombok.experimental.UtilityClass;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;

import java.util.Collection;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Amosov Maxim
 * @since 03.09.2021 : 17:26
 */
@UtilityClass
public class ParamsChecker {
    private static final int MAX_PAGE_SIZE = 2000;

    /**
     * @param value value to check
     * @param name  name of the parameter
     * @throws BadRequestException if value is blank
     */
    public void requireNonBlank(final String value, final String name) throws BadRequestException {
        if (isBlank(value)) {
            throw new BadRequestException(format("''{0}'' parameter must not be blank!", name));
        }
    }

    /**
     * @param value value to check
     * @param name  name of the parameter
     * @throws BadRequestException if value is null
     */
    public void requireNonNull(final Object value, final String name) throws BadRequestException {
        if (isNull(value)) {
            throw new BadRequestException(format("''{0}'' parameter must not be null!", name));
        }
    }

    /**
     * @param collection collection to check
     * @param name       name of the parameter
     * @throws BadRequestException if value is empty
     */
    public void requireNonEmpty(final Collection<?> collection, final String name) throws BadRequestException {
        if (isEmpty(collection)) {
            throw new BadRequestException(format("''{0}'' must not be empty!", name));
        }
    }

    /**
     * @param pageSize pageSize
     * @param name     name of the parameter
     * @throws BadRequestException if pageSize is null or less/equal to zero or greater than MAX_PAGE_SIZE
     */
    public void checkPageSize(final Integer pageSize, final String name) throws BadRequestException {
        requireNonNull(pageSize, name);
        if (pageSize <= 0 || pageSize > MAX_PAGE_SIZE) {
            throw new BadRequestException(format("''{0}'' must be greater than zero and lower/equal than {1}!", name, MAX_PAGE_SIZE));
        }
    }

    /**
     * @param pageNumber pageNumber
     * @param name       name of the parameter
     * @throws BadRequestException if pageNumber is less than zero
     */
    public void checkPageNumber(final Integer pageNumber, final String name) throws BadRequestException {
        requireNonNull(pageNumber, name);
        if (pageNumber < 0) {
            throw new BadRequestException(format("''{0}'' must be greater or equal to zero!", name));
        }
    }
}
