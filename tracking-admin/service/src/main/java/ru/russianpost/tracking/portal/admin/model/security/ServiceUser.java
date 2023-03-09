/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.security;

/**
 * POJO representation of service user.
 *
 * @author sslautin
 * @version 1.0 24.09.2015
 */
public final class ServiceUser {

    private final String username;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final boolean accountNonLocked;
    private final String authorityString;
    private final String name;
    private final String surname;
    private final String patronymic;
    private final String email;
    private final String affiliate;

    /**
     * Hidden constructor for builder purposes.
     *
     * @param builder builder of this class.
     */
    private ServiceUser(final Builder builder) {
        this.username = builder.mUsername;
        this.password = builder.mPassword;
        this.enabled = builder.mEnabled;
        this.accountNonExpired = builder.mAccountNonExpired;
        this.credentialsNonExpired = builder.mCredentialsNonExpired;
        this.accountNonLocked = builder.mAccountNonLocked;
        this.authorityString = builder.mAuthorityString;
        this.name = builder.mName;
        this.surname = builder.mSurname;
        this.patronymic = builder.mPatronymic;
        this.email = builder.mEmail;
        this.affiliate = builder.mAffiliate;
    }

    /**
     * Returns new instance of the {@link Builder}.
     *
     * @return new instance of the {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public String getAuthorityString() {
        return authorityString;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getEmail() {
        return email;
    }

    public String getAffiliate() {
        return affiliate;
    }

    /**
     * Class responsible for building instances of {@link ServiceUser}.
     */
    public static class Builder {

        private String mUsername;
        private String mPassword;
        private boolean mEnabled = true;
        private boolean mAccountNonExpired = true;
        private boolean mCredentialsNonExpired = true;
        private boolean mAccountNonLocked = true;
        private String mAuthorityString;
        private String mName;
        private String mSurname;
        private String mPatronymic;
        private String mEmail;
        private String mAffiliate;

        /**
         * Sets the builder's field.
         *
         * @param username value to set.
         * @return the builder's instance.
         */
        public Builder setUsername(final String username) {
            mUsername = username;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param password value to set.
         * @return the builder's instance.
         */
        public Builder setPassword(final String password) {
            mPassword = password;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param enabled value to set.
         * @return the builder's instance.
         */
        public Builder setEnabled(final boolean enabled) {
            mEnabled = enabled;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param accountNonExpired value to set.
         * @return the builder's instance.
         */
        public Builder setAccountNonExpired(final boolean accountNonExpired) {
            mAccountNonExpired = accountNonExpired;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param credentialsNonExpired value to set.
         * @return the builder's instance.
         */
        public Builder setCredentialsNonExpired(final boolean credentialsNonExpired) {
            mCredentialsNonExpired = credentialsNonExpired;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param accountNonLocked value to set.
         * @return the builder's instance.
         */
        public Builder setAccountNonLocked(final boolean accountNonLocked) {
            mAccountNonLocked = accountNonLocked;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param authorityString value to set.
         * @return the builder's instance.
         */
        public Builder setAuthorityString(final String authorityString) {
            mAuthorityString = authorityString;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param name value to set.
         * @return the builder's instance.
         */
        public Builder setName(final String name) {
            mName = name;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param surname value to set.
         * @return the builder's instance.
         */
        public Builder setSurname(final String surname) {
            mSurname = surname;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param patronymic value to set.
         * @return the builder's instance.
         */
        public Builder setPatronymic(final String patronymic) {
            mPatronymic = patronymic;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param email value to set.
         * @return the builder's instance.
         */
        public Builder setEmail(final String email) {
            mEmail = email;
            return this;
        }

        /**
         * Sets the builder's field.
         *
         * @param affiliate value to set.
         * @return the builder's instance.
         */
        public Builder setAffiliate(final String affiliate) {
            mAffiliate = affiliate;
            return this;
        }

        /**
         * Returns new {@link ServiceUser} with data from this builder.
         *
         * @return new {@link ServiceUser}.
         */
        public ServiceUser build() {
            return new ServiceUser(this);
        }
    }
}
