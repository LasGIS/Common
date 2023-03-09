/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.validation.validators;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.opm.OpsIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.EMPTY_INDEX_DESCRIPTION;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.INVALID_INDEX;

/**
 * @author Amosov Maxim
 * @since 22.09.2022 : 15:28
 */
@UtilityClass
public class OpmIndexesValidator {
    private static final Pattern INDEX_REGEXP = Pattern.compile(Regexp.INDEX);

    /**
     * Validates indexes format
     *
     * @param opsIndices opsIndices
     */
    public void validateFormat(final List<OpsIndex> opsIndices) {
        final List<String> invalidIndexes = new ArrayList<>();
        for (final OpsIndex opsIndex : opsIndices) {
            if (!INDEX_REGEXP.matcher(opsIndex.getIndex()).matches()) {
                invalidIndexes.add(opsIndex.getIndex());
                if (invalidIndexes.size() == 5) {
                    break;
                }
            }
        }
        if (!invalidIndexes.isEmpty()) {
            throw new HttpServiceException(BAD_REQUEST.value(), new Error(INVALID_INDEX,
                "Некоторые из индексов не соответствуют формату из 6 цифр: " + String.join(", ", invalidIndexes)
            ));
        }
    }

    /**
     * Validates indexes description
     *
     * @param opsIndices  opsIndices
     * @param extractUfps function to extract ufps
     */
    public void validateDescription(final List<OpsIndex> opsIndices, final Function<String, String> extractUfps) {
        final List<String> invalidIndexes = new ArrayList<>();
        for (final OpsIndex opsIndex : opsIndices) {
            final String ufpsName = extractUfps.apply(opsIndex.getIndex());
            opsIndex.setDescription(ufpsName);

            if (StringUtils.isBlank(ufpsName)) {
                invalidIndexes.add(opsIndex.getIndex());
                if (invalidIndexes.size() == 5) {
                    break;
                }
            }
        }
        if (!invalidIndexes.isEmpty()) {
            throw new HttpServiceException(BAD_REQUEST.value(), new Error(EMPTY_INDEX_DESCRIPTION,
                "Не удалось найти УФПС некоторых индексов:\n " + String.join(", ", invalidIndexes)
                    + ".\nВозможно, их ещё нет в справочнике. Пожалуйста, повторите запрос позднее!"
            ));
        }
    }
}
