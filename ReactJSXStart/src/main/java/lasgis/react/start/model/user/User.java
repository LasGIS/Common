/*
 * @(#)User.java
 *
 * This file contains GLONASS Union intellectual property. It
 * may contain information about GLONASS Union processes that
 * are part of the Company's competitive advantage.
 *
 * Copyright (c) 2015, Non-profit Partnership for Development
 * and Use of Navigation Technologies. All Rights Reserved
 *
 * Данный Файл содержит информацию, являющуюся интеллектуальной
 * собственностью НП «ГЛОНАСС». Он также может содержать
 * информацию о процессах, представляющих конкурентное
 * преимущество компании.
 *
 * © 2015 Некоммерческое партнерство «Содействие развитию и
 * использованию навигационных технологий». Все права защищены.
 */

package lasgis.react.start.model.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import lombok.Data;

/**
 * DB model User.
 * @author Vladimir Laskin
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 4009321252357225076L;

    private int userId;
    private String firstName;
    private String midName;
    private String lastName;
    private String login;
    private String passwordHash;
    private String email;
    private String note;
    private boolean enabled;
    private Collection<String> userRoles = new LinkedList<String>();

}
