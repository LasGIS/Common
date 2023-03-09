/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.admin.history;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.russianpost.tracking.portal.admin.security.Role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User history event description resolver.
 *
 * @author KKiryakov
 */
public final class UserHistoryEventDescriptionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(UserHistoryEventDescriptionResolver.class);

    private static final Map<UserHistoryEventType, Function<String, String>> DESCRIPTION_RESOLVERS = new EnumMap<>(UserHistoryEventType.class);

    static {
        DESCRIPTION_RESOLVERS.put(UserHistoryEventType.USER_ROLES_UPDATED, UserHistoryEventDescriptionResolver::buildRolesUpdatedDescription);
        DESCRIPTION_RESOLVERS.put(
            UserHistoryEventType.USER_INFORMATION_UPDATED,
            UserHistoryEventDescriptionResolver::buildUserInfoUpdatedDescription
        );
    }

    /** Hidden constructor. */
    private UserHistoryEventDescriptionResolver() {
    }

    /**
     * Build description string.
     *
     * @param event event
     * @return event description
     */
    public static String buildDescription(UserHistoryEvent event) {
        if (DESCRIPTION_RESOLVERS.containsKey(event.getEventType())) {
            return DESCRIPTION_RESOLVERS.get(event.getEventType()).apply(event.getEventArgs());
        } else {
            return event.getEventType().getDefaultDescription();
        }
    }

    private static String buildUserInfoUpdatedDescription(String args) {
        StringBuilder sb = new StringBuilder(UserHistoryEventType.USER_INFORMATION_UPDATED.getDefaultDescription());
        try {
            final UserInfoUpdatedEventArgs parsedArgs = new ObjectMapper().readValue(args, UserInfoUpdatedEventArgs.class);
            final UserInfo oldState = parsedArgs.getOldState();
            final UserInfo newState = parsedArgs.getNewState();
            List<String> changes = new ArrayList<>();
            if (!Objects.equals(oldState.getName(), newState.getName())) {
                changes.add("Имя: " + resolveOptionalValue(newState.getName()));
            }
            if (!Objects.equals(oldState.getSurname(), newState.getSurname())) {
                changes.add("Фамилия: " + resolveOptionalValue(newState.getSurname()));
            }
            if (!Objects.equals(oldState.getPatronymic(), newState.getPatronymic())) {
                changes.add("Отчество: " + resolveOptionalValue(newState.getPatronymic()));
            }
            if (!Objects.equals(oldState.getEmail(), newState.getEmail())) {
                changes.add("E-mail: " + resolveOptionalValue(newState.getEmail()));
            }
            sb.append(' ').append(String.join(", ", changes)).append('.');
        } catch (IOException e) {
            LOG.warn("Error to parse arguments of user information updated event. {}", e.getMessage());
        }
        return sb.toString();
    }

    private static String buildRolesUpdatedDescription(String args) {
        StringBuilder sb = new StringBuilder(UserHistoryEventType.USER_ROLES_UPDATED.getDefaultDescription());
        try {
            final UserRolesUpdatedEventArgs parsedArgs = new ObjectMapper().readValue(args, UserRolesUpdatedEventArgs.class);
            final List<String> addedRoles = parsedArgs.getAddedRoles();
            final List<String> removedRoles = parsedArgs.getRemovedRoles();
            if (addedRoles != null && !addedRoles.isEmpty()) {
                sb.append("\nДобавлен доступ: ").append(resolveRoles(addedRoles)).append('.');
            }
            if (removedRoles != null && !removedRoles.isEmpty()) {
                sb.append("\nУдален доступ: ").append(resolveRoles(removedRoles)).append('.');
            }
        } catch (IOException e) {
            LOG.warn("Error to parse arguments of roles updated event. {}", e.getMessage());
        }
        return sb.toString();
    }

    private static String resolveRoles(List<String> addedRoles) {
        return addedRoles.stream()
            .map(Role::by)
            .map(Role::getFullName)
            .collect(Collectors.joining(", "));
    }

    private static String resolveOptionalValue(String value) {
        return (value == null || value.isEmpty()) ? "<удалено>" : value;
    }
}
