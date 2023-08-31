/*
 *  @(#)MyCommandLineRunner.java  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The Class CommandLineRunner definition.
 *
 * @author VLaskin
 * @since 20.05.2023 : 23:35
 */
@Slf4j
@Service
public class MyCommandLineRunner implements CommandLineRunner, ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        log.info("args.getNonOptionArgs() = {}", args.getNonOptionArgs());
        log.info("args.getOptionNames() = {}", args.getOptionNames());
        for (final String optionName : args.getOptionNames()) {
            log.info("Option({}) = {}", optionName, args.getOptionValues(optionName));
        }
    }

    @Override
    public void run(String... args) {
        log.info("implements CommandLineRunner interface: String[{}] args = {}",
            args.length, Arrays.stream(args).map(s -> "\"" + s + "\"").collect(Collectors.joining(", ", "{ ", " };")));
    }
}
