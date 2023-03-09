/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.emsevt.manual;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.emsevt.manual.TaskInfo;

import java.util.List;

/**
 * @author Amosov Maxim
 * @since 04.08.2021 : 17:14
 */
public interface EmsevtManualSenderService {
    /**
     * @param file   MultipartFile
     * @param author author
     * @return created task info
     * @throws ServiceUnavailableException when service is unavailable
     */
    TaskInfo createTask(MultipartFile file, String author) throws ServiceUnavailableException;

    /**
     * @param author author
     * @return list of completed tasks for specified author
     * @throws ServiceUnavailableException when service is unavailable
     */
    List<TaskInfo> getTasks(String author) throws ServiceUnavailableException;

    /**
     * Returns result file
     *
     * @param downloadUrl download url
     * @return result file
     * @throws ServiceUnavailableException when service is unavailable
     */
    ResponseEntity<byte[]> getResultFile(String downloadUrl) throws ServiceUnavailableException;
}
