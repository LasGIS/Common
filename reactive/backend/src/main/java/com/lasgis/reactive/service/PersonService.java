/*
 *  @(#)PersonService.java  last: 12.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service;

import com.lasgis.reactive.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {

    /**
     * Находим всех персон по критериям (надо будет добавить)
     *
     * @return список всех персон
     */
    Flux<Person> findAll();

    /**
     * Находим персону по personId + всех персон, связанной с этой по ближайшему кругу
     *
     * @return персону
     */
    Mono<Person> findByPersonId(Long personId);

    /**
     * Сохраняем персону и связи на ближайшее окружение.
     * Если персона новая, то INSERT, а иначе - UPDATE.
     *
     * @param person персона для записи
     * @return сохраненная персона
     */
    Mono<Person> save(Person person);

    /**
     * Удаляем персону и все её связи.
     *
     * @param personId ID персоны для удаления
     * @return personId (для порядка)
     */
    Mono<Long> deleteByPersonId(Long personId);
}
