/*
 * @(#)UserDetailsServiceImpl.java
 *
 * This file contains GLONASS Union intellectual property. It
 * may contain information about GLONASS Union processes that
 * are part of the Company's competitive advantage.
 *
 * Copyright (c) 2016, Non-profit Partnership for Development
 * and Use of Navigation Technologies. All Rights Reserved
 *
 * Данный Файл содержит информацию, являющуюся интеллектуальной
 * собственностью НП «ГЛОНАСС». Он также может содержать
 * информацию о процессах, представляющих конкурентное
 * преимущество компании.
 *
 * © 2016 Некоммерческое партнерство «Содействие развитию и
 * использованию навигационных технологий». Все права защищены.
 */

package lasgis.react.start.security;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The Class UserDetailsServiceImpl.
 * @author Vladimir Laskin
 * @version 1.0
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    /** logger. */
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

//    @Autowired
//    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String login) {
        return new User(login, "password", new HashSet<GrantedAuthority>() {{
            this.add(new SimpleGrantedAuthority("ADMIN"));
            this.add(new SimpleGrantedAuthority("GUEST"));
        }});
/*
        User user;
        try {
            user = userDao.findByLogin(login);
        } catch (DaoException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new GmpAuthenticationException("User Not Found!", USER_NOT_FOUND);
        }
        if (user.getUserType() != UserType.Int) {
            throw new GmpAuthenticationException("User Has Not Internal Roles!", USER_HAS_NOT_ENOUGH_ROLES);
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserRoleCode roleCode : user.getUserRoles()) {
            authorities.add(new GrantedAuthorityGmp(roleCode));
        }
        return new UserDetailsImpl(user, Collections.unmodifiableSet(authorities));
*/
    }
}

