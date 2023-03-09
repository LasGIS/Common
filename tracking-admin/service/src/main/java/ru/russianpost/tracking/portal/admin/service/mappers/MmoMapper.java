/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.mappers;

import ru.russianpost.tracking.commons.hdps.dto.multiplace.v1.MultiplaceRpoResponse;
import ru.russianpost.tracking.portal.admin.model.mmo.MmoSearchResult;

/**
 * MmoMapper
 * @author MKitchenko
 * @version 1.0 27.11.2020
 */
public interface MmoMapper {

    /**
     * It maps response to mmo search result
     * @param resp instance of {@link MultiplaceRpoResponse}
     * @return instance of {@link MmoSearchResult}
     */
    MmoSearchResult toMmoSearchResult(MultiplaceRpoResponse resp);
}
