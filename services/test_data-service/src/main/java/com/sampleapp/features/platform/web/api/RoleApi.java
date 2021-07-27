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
import com.sampleapp.features.platform.data.model.experience.role.CreateRoleRequest;
import com.sampleapp.features.platform.data.model.experience.role.Role;
import com.sampleapp.features.platform.data.model.experience.role.UpdateRoleRequest;
import com.sampleapp.features.platform.web.service.RoleService;
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
 * com.sampleapp.features.platform.data.model.persistence.RoleEntity}.
 *
 * @author Ravindra Engle
 */
@Slf4j
@RestController
public class RoleApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Roles";

    /** Service implementation of type {@link RoleService}. */
    private final RoleService roleService;

    /**
     * Constructor.
     *
     * @param roleService Service instance of type {@link RoleService}.
     */
    public RoleApi(final RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.RoleEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.sampleapp.features.platform.data.model.persistence.RoleEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Role}.
     */
    @Operation(
            method = "createRole",
            summary = "Create a new Role.",
            description = "This API is used to create a new Role in the system.",
            tags = {RoleApi.API_TAG},
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
                        description = "Successfully created a new Role in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/roles")
    public ResponseEntity<Role> createRole(@Valid @RequestBody final CreateRoleRequest payload) {
        // Delegate to the service layer.
        final Role newInstance = roleService.createRole(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.RoleEntity} in the system.
     *
     * @param roleId Unique identifier of Role in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Role, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Role}.
     */
    @Operation(
            method = "updateRole",
            summary = "Update an existing Role.",
            description = "This API is used to update an existing Role in the system.",
            tags = {RoleApi.API_TAG},
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
                        description = "Successfully updated an existing Role in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/roles/{roleId}")
    public ResponseEntity<Role> updateRole(
            @PathVariable(name = "roleId") final String roleId,
            @Valid @RequestBody final UpdateRoleRequest payload) {
        // Delegate to the service layer.
        final Role updatedInstance = roleService.updateRole(roleId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.sampleapp.features.platform.data.model.persistence.RoleEntity} in the system.
     *
     * @param roleId Unique identifier of Role in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Role}.
     */
    @Operation(
            method = "findRole",
            summary = "Find an existing Role.",
            description = "This API is used to find an existing Role in the system.",
            tags = {RoleApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Role in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/roles/{roleId}")
    public ResponseEntity<Role> findRole(@PathVariable(name = "roleId") final String roleId) {
        // Delegate to the service layer.
        final Role matchingInstance = roleService.findRole(roleId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.sampleapp.features.platform.data.model.persistence.RoleEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Role
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllRoles",
            summary = "Find all Roles.",
            description = "This API is used to find all Roles in the system.",
            tags = {RoleApi.API_TAG},
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
                                "Successfully retrieved the Roles in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/roles")
    public ResponseEntity<Page<Role>> findAllRoles(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Role> matchingInstances = roleService.findAllRoles(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.RoleEntity} in the system.
     *
     * @param roleId Unique identifier of Role in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.sampleapp.features.platform.data.model.persistence.RoleEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteRole",
            summary = "Delete an existing Role.",
            description = "This API is used to delete an existing Role in the system.",
            tags = {RoleApi.API_TAG},
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
                        description = "Successfully deleted an existing Role in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/roles/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable(name = "roleId") final String roleId) {
        // Delegate to the service layer.
        final String deletedInstance = roleService.deleteRole(roleId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
