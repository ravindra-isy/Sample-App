/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.web.api;

import com.sampleapp.commons.data.utils.PageUtils;
import com.sampleapp.commons.web.api.AbstractApi;
import com.sampleapp.commons.web.configuration.properties.ApiDocumentationSettings;
import com.sampleapp.features.platform.data.model.experience.user.CreateUserRequest;
import com.sampleapp.features.platform.data.model.experience.user.UpdateUserRequest;
import com.sampleapp.features.platform.data.model.experience.user.User;
import com.sampleapp.features.platform.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link
 * com.sampleapp.features.platform.data.model.persistence.UserEntity}.
 *
 * @author Ravindra Engle
 */
@Slf4j
@RestController
public class UserApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Users";

    /** Service implementation of type {@link UserService}. */
    private final UserService userService;

    /**
     * Constructor.
     *
     * @param userService Service instance of type {@link UserService}.
     */
    public UserApi(final UserService userService) {
        this.userService = userService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.UserEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.sampleapp.features.platform.data.model.persistence.UserEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link User}.
     */
    @Operation(
            method = "createUser",
            summary = "Create a new User.",
            description = "This API is used to create a new User in the system.",
            tags = {UserApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successfully created a new User in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody final CreateUserRequest payload) {
        // Delegate to the service layer.
        final User newInstance = userService.createUser(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.UserEntity} in the system.
     *
     * @param userId Unique identifier of User in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing User, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link User}.
     */
    @Operation(
            method = "updateUser",
            summary = "Update an existing User.",
            description = "This API is used to update an existing User in the system.",
            tags = {UserApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully updated an existing User in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/users/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable(name = "userId") final Integer userId,
            @Valid @RequestBody final UpdateUserRequest payload) {
        // Delegate to the service layer.
        final User updatedInstance = userService.updateUser(userId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.sampleapp.features.platform.data.model.persistence.UserEntity} in the system.
     *
     * @param userId Unique identifier of User in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link User}.
     */
    @Operation(
            method = "findUser",
            summary = "Find an existing User.",
            description = "This API is used to find an existing User in the system.",
            tags = {UserApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the details of an existing User in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<User> findUser(@PathVariable(name = "userId") final Integer userId) {
        // Delegate to the service layer.
        final User matchingInstance = userService.findUser(userId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.sampleapp.features.platform.data.model.persistence.UserEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type User
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllUsers",
            summary = "Find all Users.",
            description = "This API is used to find all Users in the system.",
            tags = {UserApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the Users in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/users")
    public ResponseEntity<Page<User>> findAllUsers(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<User> matchingInstances = userService.findAllUsers(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.UserEntity} in the system.
     *
     * @param userId Unique identifier of User in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.sampleapp.features.platform.data.model.persistence.UserEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteUser",
            summary = "Delete an existing User.",
            description = "This API is used to delete an existing User in the system.",
            tags = {UserApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully deleted an existing User in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity<Integer> deleteUser(@PathVariable(name = "userId") final Integer userId) {
        // Delegate to the service layer.
        final Integer deletedInstance = userService.deleteUser(userId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
