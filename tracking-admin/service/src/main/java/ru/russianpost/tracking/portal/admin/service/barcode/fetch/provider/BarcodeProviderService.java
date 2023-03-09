/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider;

import org.springframework.http.ResponseEntity;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderUser;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.Booking;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BookingInternational;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.Container;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.DefaultContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.IndexContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.InternationalContainer;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.BookingInternalCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.BookingInternationalCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.ChangeRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.DeleteContainerRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.FileUsersCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.IndexMonthContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.ResetPasswordRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.UpdateContainerRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.UserCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.UserUpdateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BatchRegistrationResponseData;
import ru.russianpost.tracking.portal.admin.model.ui.HistoryEntry;

import java.util.List;
import java.util.Map;

/**
 * BarcodeProviderService interface.
 *
 * @author KKiryakov
 */
public interface BarcodeProviderService {

    /**
     * Gets ranges by index
     *
     * @param index index
     * @return ranges
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<Container> getRanges(int index) throws ServiceUnavailableException;

    /**
     * Gets bookings by index and month
     *
     * @param index index
     * @param year  year
     * @param month month
     * @return bookings
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<Booking> getBookings(int index, int year, int month) throws ServiceUnavailableException;

    /**
     * Gets default configuration of barcode provider
     *
     * @return default configuration
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    DefaultContainerConfiguration getDefaultConfiguration() throws ServiceUnavailableException;

    /**
     * Gets configuration defined for specified index
     *
     * @param index index
     * @return index configuration
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    IndexContainerConfiguration getIndexConfiguration(int index) throws ServiceUnavailableException;

    /**
     * Updates default configuration of barcode provider
     *
     * @param configuration new configuration
     * @param author        author of the change
     * @param comment       comment for the change
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void updateDefaultConfiguration(DefaultContainerConfiguration configuration, String author, String comment) throws ServiceUnavailableException;

    /**
     * Updates configuration defined for specified index
     *
     * @param index         index
     * @param configuration new index configuration
     * @param author        author of the change
     * @param comment       comment for the change
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void updateIndexConfiguration(int index, IndexContainerConfiguration configuration, String author, String comment)
        throws ServiceUnavailableException;

    /**
     * Gets changes history of default configuration.
     *
     * @return changes history of default configuration.
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<HistoryEntry> getDefaultConfigurationHistory() throws ServiceUnavailableException;

    /**
     * Gets changes history of index configuration.
     *
     * @param index index
     * @return changes history of index configuration.
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<HistoryEntry> getIndexConfigurationHistory(int index) throws ServiceUnavailableException;

    /**
     * Defines new index configuration for specific month
     *
     * @param index         index
     * @param year          year
     * @param month         month
     * @param configuration configuration
     * @param author        author
     * @param comment       comment
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void addIndexMonthConfiguration(int index, int year, int month, IndexMonthContainerConfiguration configuration, String author, String comment)
        throws ServiceUnavailableException;

    /**
     * Updates index configuration of specific month
     *
     * @param index         index
     * @param year          year
     * @param month         month
     * @param configuration configuration
     * @param author        author
     * @param comment       comment
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void updateIndexMonthConfiguration(int index, int year, int month, IndexMonthContainerConfiguration configuration, String author, String comment)
        throws ServiceUnavailableException;

    /**
     * Deletes index configuration of specific month
     *
     * @param index   index
     * @param year    year
     * @param month   month
     * @param request delete request (author, comment)
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void deleteIndexMonthConfiguration(int index, int year, int month, ChangeRequest request) throws ServiceUnavailableException;

    /**
     * Creates barcode provider user.
     *
     * @param userCreateRequest create request
     * @return created barcode provider user.
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    BarcodeProviderUser createUser(UserCreateRequest userCreateRequest) throws ServiceUnavailableException;

    /**
     * Creates barcode provider users from file.
     *
     * @param fileUsersCreateRequest create request
     * @return map row number to user credentials.
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    Map<Integer, BatchRegistrationResponseData> createUsers(FileUsersCreateRequest fileUsersCreateRequest) throws ServiceUnavailableException;

    /**
     * Updates barcode provider user.
     *
     * @param id                user ID
     * @param userUpdateRequest update request
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void updateUser(long id, UserUpdateRequest userUpdateRequest) throws ServiceUnavailableException;

    /**
     * Returns user by user id.
     *
     * @param userId user id
     * @return barcode provider user
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    BarcodeProviderUser getUser(long userId) throws ServiceUnavailableException;

    /**
     * Finds users by query string
     *
     * @param query query (substring of username/inn/companyName)
     * @param count limit of results
     * @return list of suggestions (partially filled users)
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<BarcodeProviderUser> findUser(String query, int count) throws ServiceUnavailableException;

    /**
     * Deletes booking by its ID
     *
     * @param id      booking id
     * @param request delete request (author, comment)
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void deleteBooking(long id, ChangeRequest request) throws ServiceUnavailableException;

    /**
     * Returns xml for internal booking.
     *
     * @param id booking id
     * @return response
     */
    ResponseEntity<byte[]> getBookingXml(long id);

    /**
     * Delete container.
     *
     * @param index   index
     * @param year    year
     * @param month   month
     * @param request request
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void deleteContainer(int index, int year, int month, DeleteContainerRequest request) throws ServiceUnavailableException;

    /**
     * Updates container
     *
     * @param index   index
     * @param year    year
     * @param month   month
     * @param request request
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void updateContainer(int index, int year, int month, UpdateContainerRequest request) throws ServiceUnavailableException;

    /**
     * Finds bookings by query parameters
     *
     * @param start     bookingStart
     * @param mailType  mailType
     * @param letterBeg letterBeg
     * @param end       bookingEnd
     * @return bookings
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<BookingInternational> findBookingsInternational(int start, int end, String mailType, String letterBeg) throws ServiceUnavailableException;

    /**
     * Finds bookings by query parameters
     *
     * @param index     index
     * @param inn       inn
     * @param start     bookingStart
     * @param end       bookingEnd
     * @param mailType  mailType
     * @param letterBeg letterBeg
     * @return bookings
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<BookingInternational> findBookingsInternationalAdvanced(
        String index, String inn, String start, String end, String mailType, String letterBeg
    )
        throws ServiceUnavailableException;

    /**
     * Returns xml for international booking.
     *
     * @param id booking id
     * @return response
     */
    ResponseEntity<byte[]> getBookingInternationalXml(long id);

    /**
     * Deletes international booking by its ID
     *
     * @param id      booking id
     * @param request delete request (author, comment)
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void deleteBookingInternational(long id, ChangeRequest request) throws ServiceUnavailableException;

    /**
     * Creates internal booking
     *
     * @param request create request
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void createBookingInternal(BookingInternalCreateRequest request) throws ServiceUnavailableException;

    /**
     * Creates international booking
     *
     * @param request create request
     * @return Booking international
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    BookingInternational createBookingInternational(BookingInternationalCreateRequest request) throws ServiceUnavailableException;

    /**
     * Returns allocation size for specified index.
     *
     * @param index index
     * @return allocation size
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    Integer getAllocationSize(int index) throws ServiceUnavailableException;

    /**
     * Resets user password
     *
     * @param userId  user id
     * @param request request contains list of notifications emails
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void resetUserPassword(String userId, ResetPasswordRequest request) throws ServiceUnavailableException;

    /**
     * Get all international containers
     *
     * @return all international containers (S10)
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<InternationalContainer> getInternationalContainers() throws ServiceUnavailableException;
}
