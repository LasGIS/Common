/*
 *  @(#)PersonController.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.model.Person;
import com.lasgis.reactive.model.errors.Error;
import com.lasgis.reactive.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The Class PersonController definition.
 *
 * @author VLaskin
 * @since 13.09.2023 : 13:00
 */
@Slf4j
@Tag(name = "Person", description = "Add, Edit or Delete Person")
@RestController
@RequestMapping("api/v2.0/person")
public class PersonController {
    private final PersonService service;

    /**
     * Constructor
     *
     * @param service service
     */
    @Autowired
    public PersonController(final PersonService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Flux<Person>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
        summary = "Find all Person",
        description = "Find all Person")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = Person.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request. Check input data",
            content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Order not found",
            content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = Error.class)))})
    @GetMapping(path = "{personId}")
    public ResponseEntity<Mono<Person>> getPersonById(@PathVariable("personId") final Long personId) {
        return ResponseEntity.ok(service.findByPersonId(personId));
    }

    @Operation(
        summary = "Looks for orders status.",
        description = "Looks for Orders Status")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = Person.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request. Check input data",
            content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Order not found",
            content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = Error.class)))})
    @PostMapping()
    public ResponseEntity<Mono<Person>> createNewPerson(@RequestBody Person newPerson) {
        return ResponseEntity.ok(service.save(newPerson));
    }

    @Operation(
        summary = "Looks for orders status.",
        description = "Looks for Orders Status")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = Person.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request. Check input data",
            content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Order not found",
            content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = Error.class)))})
    @PutMapping("{personId}")
    public ResponseEntity<Mono<Person>> replaceEmployee(@RequestBody Person newPerson, @PathVariable Long personId) {
        return ResponseEntity.ok(service.findByPersonId(personId)
            .flatMap(person -> {
                person.setPersonId(newPerson.getPersonId());
                person.setFirstName(newPerson.getFirstName());
                person.setLastName(newPerson.getLastName());
                person.setMiddleName(newPerson.getMiddleName());
                person.setSex(newPerson.getSex());
                person.setRelations(newPerson.getRelations());
                return service.save(person);
            })
            .switchIfEmpty(
                Mono.defer(() -> {
                    newPerson.setPersonId(personId);
                    return service.save(newPerson);
                })
            ));
    }

    @Operation(
        summary = "Looks for orders status.",
        description = "Looks for Orders Status")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = Person.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request. Check input data",
            content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Order not found",
            content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = Error.class)))})
    @DeleteMapping("{personId}")
    ResponseEntity<Mono<Long>> deleteEmployee(@PathVariable Long personId) {
        return ResponseEntity.ok(service.deleteByPersonId(personId));
    }
}
