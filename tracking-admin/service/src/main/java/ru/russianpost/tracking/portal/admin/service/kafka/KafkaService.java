/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.kafka;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Dec 03, 2015)
 */
public interface KafkaService {

    /**
     * Produces message to tracking input topic.
     *
     * @param key message key.
     * @param value message value.
     * @throws Exception if cannot confirm that record has been sent.
     */
    void produceToInput(final byte[] key, final byte[] value) throws Exception;

    /**
     * Produces message to kafka Alteration topic.
     *
     * @param key message key.
     * @param value message value.
     * @throws Exception if cannot confirm that record has been sent.
     */
    void produceToAlteration(byte[] key, byte[] value) throws Exception;
}
