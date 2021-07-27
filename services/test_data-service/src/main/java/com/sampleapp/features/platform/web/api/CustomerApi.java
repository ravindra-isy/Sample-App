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
import com.sampleapp.features.platform.data.model.experience.customer.CreateCustomerRequest;
import com.sampleapp.features.platform.data.model.experience.customer.Customer;
import com.sampleapp.features.platform.data.model.experience.customer.UpdateCustomerRequest;
import com.sampleapp.features.platform.web.service.CustomerService;
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
 * com.sampleapp.features.platform.data.model.persistence.CustomerEntity}.
 *
 * @author Ravindra Engle
 */
@Slf4j
@RestController
public class CustomerApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Customers";

    /** Service implementation of type {@link CustomerService}. */
    private final CustomerService customerService;

    /**
     * Constructor.
     *
     * @param customerService Service instance of type {@link CustomerService}.
     */
    public CustomerApi(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.CustomerEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.sampleapp.features.platform.data.model.persistence.CustomerEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Customer}.
     */
    @Operation(
            method = "createCustomer",
            summary = "Create a new Customer.",
            description = "This API is used to create a new Customer in the system.",
            tags = {CustomerApi.API_TAG},
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
                        description = "Successfully created a new Customer in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/customers")
    public ResponseEntity<Customer> createCustomer(
            @Valid @RequestBody final CreateCustomerRequest payload) {
        // Delegate to the service layer.
        final Customer newInstance = customerService.createCustomer(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.CustomerEntity} in the system.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing Customer, which needs to
     *     be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Customer}.
     */
    @Operation(
            method = "updateCustomer",
            summary = "Update an existing Customer.",
            description = "This API is used to update an existing Customer in the system.",
            tags = {CustomerApi.API_TAG},
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
                        description = "Successfully updated an existing Customer in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/customers/{customerCustomerid}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @Valid @RequestBody final UpdateCustomerRequest payload) {
        // Delegate to the service layer.
        final Customer updatedInstance =
                customerService.updateCustomer(customerCustomerid, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.sampleapp.features.platform.data.model.persistence.CustomerEntity} in the system.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Customer}.
     */
    @Operation(
            method = "findCustomer",
            summary = "Find an existing Customer.",
            description = "This API is used to find an existing Customer in the system.",
            tags = {CustomerApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Customer in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/customers/{customerCustomerid}")
    public ResponseEntity<Customer> findCustomer(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid) {
        // Delegate to the service layer.
        final Customer matchingInstance = customerService.findCustomer(customerCustomerid);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.sampleapp.features.platform.data.model.persistence.CustomerEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Customer based on the provided pagination settings.
     */
    @Operation(
            method = "findAllCustomers",
            summary = "Find all Customers.",
            description = "This API is used to find all Customers in the system.",
            tags = {CustomerApi.API_TAG},
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
                                "Successfully retrieved the Customers in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/customers")
    public ResponseEntity<Page<Customer>> findAllCustomers(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Customer> matchingInstances = customerService.findAllCustomers(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.CustomerEntity} in the system.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, which needs to be
     *     deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.sampleapp.features.platform.data.model.persistence.CustomerEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteCustomer",
            summary = "Delete an existing Customer.",
            description = "This API is used to delete an existing Customer in the system.",
            tags = {CustomerApi.API_TAG},
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
                        description = "Successfully deleted an existing Customer in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/customers/{customerCustomerid}")
    public ResponseEntity<Integer> deleteCustomer(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid) {
        // Delegate to the service layer.
        final Integer deletedInstance = customerService.deleteCustomer(customerCustomerid);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
