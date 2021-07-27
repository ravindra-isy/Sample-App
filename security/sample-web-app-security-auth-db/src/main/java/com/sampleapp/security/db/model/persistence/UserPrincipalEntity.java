/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.security.db.model.persistence;

import com.sampleapp.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import com.sampleapp.security.userdetails.UserPrincipal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementation that maps the "user" table in the database to an entity in the ORM world.
 *
 * @author Ravindra Engle
 */
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@Table(name = "user")
@Entity
public class UserPrincipalEntity extends AbstractIdGeneratedEntity<Integer> {

    /** GrantedAuthority format as required by Spring Security. */
    private static final String GRANTED_AUTHORITY_FORMAT = "ROLE_{0}";

    /** User name of the user. */
    @Column(name = "username", nullable = false, length = 32)
    private String username;

    /** Password of the user. */
    @Column(name = "password", nullable = false)
    private String password;

    /** Reference to the roles. */
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Collection<UserRoleEntity> roles;

    /**
     * This method returns a collection of authority names (i.e. ROLE_[role name]), which are the
     * roles assigned to this user and prefixed by 'ROLE_'.
     *
     * @return Collection of authority names.
     */
    public Collection<String> getAuthorityNamesForAssignedRoles() {
        return Optional.ofNullable(roles).orElse(Collections.emptySet()).stream()
                .map(
                        role ->
                                MessageFormat.format(
                                        UserPrincipalEntity.GRANTED_AUTHORITY_FORMAT,
                                        role.getName()))
                .collect(Collectors.toSet());
    }

    /**
     * This method returns a collection of role names that are assigned to this user.
     *
     * @return Collection of role names.
     */
    public Collection<String> getAssignedRoleNames() {
        return Optional.ofNullable(roles).orElse(Collections.emptySet()).stream()
                .map(UserRoleEntity::getName)
                .collect(Collectors.toSet());
    }

    /**
     * This method attempts to transform the provided user entity (of type {@link
     * UserPrincipalEntity}) to an instance of type {@link
     * org.springframework.security.core.userdetails.UserDetails}.
     *
     * <p>The default implementation returns an instance of {@link UserPrincipal}. Subclasses can
     * override to provide their own implementations of {@link
     * org.springframework.security.core.userdetails.UserDetails}.
     *
     * @return User details instance for the provided user.
     */
    public UserDetails toUserPrincipal() {
        // Build the base user details using the above roles as the authority list.
        // Get all the roles associated to the concerned user.
        final Collection<String> roleNames = getAuthorityNamesForAssignedRoles();

        final UserDetails baseUserDetails =
                User.builder()
                        .username(getUsername())
                        .password(getPassword())
                        .authorities(roleNames.toArray(new String[0]))
                        .build();
        // Delegate to the below method to create and return an instance of UserPrincipal.
        return toUserPrincipal(baseUserDetails);
    }

    /**
     * This method attempts to transform the provided user entity (of type {@link
     * UserPrincipalEntity}) to an instance of type {@link UserDetails}.
     *
     * <p>The default implementation returns an instance of {@link UserPrincipal}. Subclasses can
     * override to provide their own implementations of {@link
     * org.springframework.security.core.userdetails.UserDetails}.
     *
     * @param userDetails User details instance of type {@link
     *     org.springframework.security.core.userdetails.UserDetails}.
     * @return User details instance for the provided user.
     */
    public UserDetails toUserPrincipal(final UserDetails userDetails) {
        return new UserPrincipal(userDetails, getId(), getUsername(), getPassword());
    }
}
