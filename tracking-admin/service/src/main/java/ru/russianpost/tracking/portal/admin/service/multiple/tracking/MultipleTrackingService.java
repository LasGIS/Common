/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.multiple.tracking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.multiple.tracking.ResultScope;
import ru.russianpost.tracking.portal.admin.model.multiple.tracking.TaskCreated;
import ru.russianpost.tracking.portal.admin.model.multiple.tracking.TaskInfo;

import java.util.List;

/**
 * MultipleTrackingService interface.
 *
 * @author KKiryakov
 */
public interface MultipleTrackingService {

    /**
     * Create multiple tracking task.
     * @param file             MultipartFile
     * @param hiddenOperations include hidden operations if true
     * @param resultScope      result scope
     * @param csvShowColumns   string with Column separated by comma
     * @param author           author
     * @throws ServiceUnavailableException on error
     * @return task created info
     */
    TaskCreated createTask(
        MultipartFile file,
        boolean hiddenOperations,
        ResultScope resultScope,
        String csvShowColumns,
        String author
    ) throws ServiceUnavailableException;

    /**
     * List completed tasks.
     *
     * @param author author
     * @throws ServiceUnavailableException on error
     * @return list of completed tasks for specified actor
     */
    List<TaskInfo> listCompletedTasks(String author) throws ServiceUnavailableException;

    /**
     * Returns result file.
     *
     * @param downloadUrl download url
     * @throws ServiceUnavailableException on error
     * @return result file.
     */
    ResponseEntity<byte[]> getResultFile(String downloadUrl) throws ServiceUnavailableException;
}
