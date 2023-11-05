/*
 *  @(#)YandexHistoryController.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.controller.history;

import com.lasgis.reactive.springdoc.model.history.YandexErrorHistoryRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.lasgis.reactive.springdoc.model.history.GetYandexOrderHistoryRestRequest;
import com.lasgis.reactive.springdoc.model.history.GetYandexOrderHistoryRestResponse;

import java.security.Principal;

@Tag(name = "Yandex Order")
@RestController
@RequestMapping(path = "/v1.0/orders/yandex",
    produces = MediaType.TEXT_XML_VALUE,
    consumes = MediaType.TEXT_XML_VALUE)
@RequiredArgsConstructor
public class YandexHistoryController {

    private final YandexHistoryRestApiContextHolder contextTool;

    @Operation(
        summary = "Looks for Order History",
        description = "Looks for Order History")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = GetYandexOrderHistoryRestResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request. Check input data",
            content = @Content(schema = @Schema(implementation = YandexErrorHistoryRestResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "User is not authorized",
            content = @Content(schema = @Schema(implementation = YandexErrorHistoryRestResponse.class))),
        @ApiResponse(
            responseCode = "403",
            description = "User does not have enough rights to perform this operation",
            content = @Content(schema = @Schema(implementation = YandexErrorHistoryRestResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Order not found",
            content = @Content(schema = @Schema(implementation = YandexErrorHistoryRestResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = YandexErrorHistoryRestResponse.class)))})
    @PostMapping("/get-order-history")
    public Mono<ResponseEntity<GetYandexOrderHistoryRestResponse>> getOrderHistory(
        @RequestBody final GetYandexOrderHistoryRestRequest request,
        final ServerWebExchange serverWebExchange,
        final Principal principal
    ) {
        return contextTool
            .initContext(request, serverWebExchange)
            .map(req -> {
                var res = new GetYandexOrderHistoryRestResponse();
                res.setUniq(req.getUniq());
                return res;
            })
            .map(ResponseEntity::ok);
    }
}
