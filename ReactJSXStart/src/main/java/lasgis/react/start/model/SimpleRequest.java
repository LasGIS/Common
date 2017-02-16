/*
 * @(#)SimpleRequest.java
 *
 * This file contains GLONASS Union intellectual property. It
 * may contain information about GLONASS Union processes that
 * are part of the Company's competitive advantage.
 *
 * Copyright (c) 2017, Non-profit Partnership for Development
 * and Use of Navigation Technologies. All Rights Reserved
 *
 * Данный Файл содержит информацию, являющуюся интеллектуальной
 * собственностью НП «ГЛОНАСС». Он также может содержать
 * информацию о процессах, представляющих конкурентное
 * преимущество компании.
 *
 * © 2017 Некоммерческое партнерство «Содействие развитию и
 * использованию навигационных технологий». Все права защищены.
 */

package lasgis.react.start.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class SimpleRequest.
 * @author Vladimir Laskin
 * @version 1.0
 */
@Data
@AllArgsConstructor(staticName = "of")
public class SimpleRequest {
    private String state;
    private String info;
}
