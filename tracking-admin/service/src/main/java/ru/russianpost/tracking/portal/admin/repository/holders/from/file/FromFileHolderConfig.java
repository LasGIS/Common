/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.file;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.russianpost.tracking.commons.utils.ConfigUtils;
import ru.russianpost.tracking.portal.admin.ConfigHolder;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotificationType;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.operations.OperationInfo;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.operations.OperationOrderHolder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

/**
 * @author MKitchenko
 * @version 1.0 (March 01, 2019)
 */
@Configuration
public class FromFileHolderConfig {

    /**
     * Indexes to countries map holder
     * @return new holder
     */
    @Bean
    public IndexCountryHolder indexCountryHolder() {
        final Map<String, Integer> indexCountryMap = ConfigHolder.CONFIG.getObjectList("country.map").stream()
            .map(ConfigObject::toConfig)
            .collect(toMap(item -> item.getString("index"), item -> item.getInt("country")));
        return new IndexCountryHolder(indexCountryMap);
    }

    /**
     * SMS notification types holder.
     * @return sms notification types holder
     */
    @Bean
    public SmsNotificationTypesHolder smsNotificationTypesHolder() {
        final Map<Integer, SmsNotificationType> map = ConfigHolder.CONFIG.getObjectList("sms.notification.types")
            .stream()
            .map(ConfigObject::toConfig)
            .map(item -> new SmsNotificationType(item.getInt("id"), item.getString("value")))
            .collect(toMap(SmsNotificationType::getId, Function.identity()));
        return new SmsNotificationTypesHolder(map);
    }

    /**
     * SMS notification type id to order map holder.
     * @return sms notification type id to order map holder
     */
    @Bean
    public SmsTypeOrderHolder smsTypeOrderHolder() {
        final List<Integer> smsNotificationTypesOrderingList = ConfigHolder.CONFIG.getIntList("sms.notification.ordering");
        final Map<Integer, Integer> map = IntStream.range(0, smsNotificationTypesOrderingList.size())
            .boxed()
            .collect(toMap(smsNotificationTypesOrderingList::get, item -> item + 1));
        return new SmsTypeOrderHolder(map);
    }

    /**
     * Hide reason descriptions holder.
     * @return hide reason descriptions holder
     */
    @Bean
    public HideReasonDescriptionsHolder hideReasonDescriptionsHolder() {
        final Map<String, String> map = ConfigHolder.CONFIG.getObjectList("hide.reasons")
            .stream()
            .map(ConfigObject::toConfig)
            .collect(toMap(c -> c.getString("id"), c -> c.getString("value")));
        return new HideReasonDescriptionsHolder(map);
    }

    /**
     * Operation order list holder.
     * @return operation order list holder
     */
    @Bean
    public OperationOrderHolder operationOrderHolder() {
        final List<OperationInfo> operationOrderList = ConfigHolder.CONFIG.getObjectList("operation.order")
            .stream()
            .map(c -> makeOperationInfo(c.toConfig()))
            .collect(Collectors.toList());
        return new OperationOrderHolder(operationOrderList);
    }

    /**
     * Allowed to add operations holder.
     * @return allowed to create operations holder
     */
    @Bean
    public AllowedToAddOperationsHolder allowedToAddOperationsHolder() {
        return new AllowedToAddOperationsHolder(ConfigUtils.extractOperations(ConfigHolder.CONFIG, "allowed.to.create.operations"));
    }

    private OperationInfo makeOperationInfo(final Config config) {
        List<Integer> operAttr;
        try {
            operAttr = config.getIntList("operAttr");
        } catch (ConfigException.Missing e) {
            operAttr = Collections.emptyList();
        }
        return new OperationInfo(config.getInt("operType"), operAttr);
    }
}
