/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.russianpost.tracking.portal.admin.controller.exception.UnrecognizedRoleException;

import java.util.stream.Stream;

/**
 * @author Roman Prokhorov
 * @author KKiryakov
 * @version 2.0
 */
@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {

    /**
     * Tracking user may use tracking
     */
    ROLE_TRACKING_USER("Отслеживание", "Позволяет пользователю отслеживать почтовые отправления"),
    /**
     * EMS user may use EMS input page
     */
    ROLE_EMS_USER("Регистрация EMS", "Позволяет пользователю регистрировать EMS отправления"),
    /**
     * International input user may use international input page
     */
    ROLE_MRPO_USER("Регистрация МРПО", "Позволяет пользователю регистрировать международные отправления"),
    /**
     * Corrections
     */
    ROLE_CORR_USER("Корректировки (и журнал)", "Позволяет корректировать операции отслеживания"),
    /**
     * Security Officer
     */
    ROLE_POSTAL_SECURITY_OFFICER("Сотрудник Почтовой Безопасности", "Позволяет просматривать скрытую информацию"),
    /**
     * Profile write operations
     */
    ROLE_PROFILE_MANAGE_USER("Управление пользователями", "Позволяет управлять профилями клиентов трекинга"),
    /**
     * Profile view
     */
    ROLE_PROFILE_VIEW_USER("Просмотр информации о пользователях", "Позволяет просматривать профили клиентов трекинга"),
    /**
     * Profile statistics view
     */
    ROLE_PROFILE_STAT_USER("Профили трекинга - Статистика", "Позволяет просматривать статистику клиентов трекинга"),
    /**
     * Barcode provider role
     */
    ROLE_BARCODE_PROVIDER(
        "Управление диапазонами выдачи ШПИ",
        "Позволяет просматривать выдачи ШПИ и управлять конфигурацией диапазонов для автовыделения"
    ),
    /**
     * Barcode provider role
     */
    ROLE_BARCODE_PROVIDER_VIEW_ONLY(
        "Просмотр диапазонов выдачи ШПИ",
        "Позволяет просматривать выдачи ШПИ"
    ),
    /**
     * Barcode provider role
     */
    ROLE_BARCODE_PROVIDER_SOAP(
        "Оператор поддержки ППП Партионная почта",
        "Позволяет управлять пользователями сервиса выдачи ШПИ и выдачами ШПИ"
    ),
    /**
     * Admin role
     */
    ROLE_ADMIN("Администрирование", "Позволяет управлять пользователями служебной консоли"),
    /**
     * Online Payment Mark role
     */
    ROLE_ONLINE_PAYMENT_MARK("Знак онлайн оплаты", "Позволяет просматривать информацию о знаке онлайн оплаты"),
    /**
     * Barcode Automatization role
     */
    ROLE_BARCODE_AUTOMATIZATION_USER(
        "Пользователь автоматизации ШПИ по назначенному УФПС",
        "Позволяет запрашивать диапазоны ШПИ для назначенных УФПС"
    ),
    /**
     * Barcode Automatization role
     */
    ROLE_BARCODE_AUTOMATIZATION_ADMIN_USER_TO_UFPS(
        "Назначение УФПС пользователям автоматизации ШПИ",
        "Позволяет назначать УФПС пользователям"
    ),
    /**
     * Barcode Automatization role
     */
    ROLE_BARCODE_AUTOMATIZATION_VIEW_ONLY(
        "Просмотр диапазонов ШПИ для УФПС",
        "Позволяет просматривать выделенные диапазоны ШПИ для УФПС"
    ),
    /**
     * Authorized Operator Support role
     */
    ROLE_AUTHORIZED_OPERATOR_SUPPORT("Поддержка УО", "Позволяет контролировать начисление таможенных платежей для МПО от торговых площадок"),
    /**
     * Elasticsearch Report Generator statistics view
     */
    ROLE_ELASTIC_STATISTIC(
        "Статистика Elasticsearch",
        "Позволяет просматривать статистику данных сервиса Elastic Report Generator"
    ),
    /**
     * Emsevt manual sender operator
     */
    ROLE_EMSEVT_MANUAL_SENDER_OPERATOR(
        "Ручная отправка EMSEVT (EMG-сегментов)",
        "Позволяет формировать задачи на ручную отправку EMSEVT в ВПС по запросу менеджеров БМБ"
    ),

    /**
     * Search customs declaration operator
     */
    ROLE_CUSTOMS_DECLARATION(
        "Отслеживание таможенных деклараций",
        "Позволяет пользователю отслеживать таможенные декларации"
    ),

    /**
     * Search History by phone operator
     */
    ROLE_TRACKING_BY_PHONE(
        "Отслеживание почтовых отправлений по номеру телефона",
        "Позволяет пользователю отслеживать почтовые отправления по номеру телефона"
    ),

    /**
     * UPU monitoring operator
     */
    ROLE_UPU_MONITORING(
        "Отслеживание сообщений по обмену данными с ВПС",
        "Позволяет пользователю отслеживать сообщения по обмену данными с ВПС"
    ),

    /**
     * OPS user generator for OPM service
     */
    ROLE_OPS_USER_GENERATOR(
        "Генерация логинов для ЗОО",
        "Позволяет пользователю генерировать новые логины для сервиса ЗОО"
    ),

    /**
     * Unknown role
     */
    UNKNOWN("Неизвестная роль", null);

    private final String fullName;
    private final String description;

    /**
     * Returns role by code
     *
     * @param code code
     * @return role, or unknown if role unrecognized
     */
    public static Role by(String code) {
        return Stream.of(values()).filter(r -> r.name().equals(code)).findAny().orElse(UNKNOWN);
    }

    /**
     * Returns role by name or throw Exception
     *
     * @param name name
     * @return role, or Exception if role is not recognized
     * @throws UnrecognizedRoleException if role is not recognized
     */
    public static Role byNameOrThrow(final String name) throws UnrecognizedRoleException {
        return Stream.of(values())
            .filter(r -> r.name().equals(name)).findAny()
            .orElseThrow(() -> new UnrecognizedRoleException(name));
    }

    @Override
    public String toString() {
        return String.format("Role{code='%s', fullName='%s', description='%s'}", name(), fullName, description);
    }
}
