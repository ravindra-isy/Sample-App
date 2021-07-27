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
import com.sampleapp.features.platform.data.model.experience.invoices.CreateInvoicesRequest;
import com.sampleapp.features.platform.data.model.experience.invoices.Invoices;
import com.sampleapp.features.platform.data.model.experience.invoices.UpdateInvoicesRequest;
import com.sampleapp.features.platform.web.service.InvoicesService;
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
 * com.sampleapp.features.platform.data.model.persistence.InvoicesEntity}.
 *
 * @author Ravindra Engle
 */
@Slf4j
@RestController
public class InvoicesApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Invoiceses";

    /** Service implementation of type {@link InvoicesService}. */
    private final InvoicesService invoicesService;

    /**
     * Constructor.
     *
     * @param invoicesService Service instance of type {@link InvoicesService}.
     */
    public InvoicesApi(final InvoicesService invoicesService) {
        this.invoicesService = invoicesService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.InvoicesEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.sampleapp.features.platform.data.model.persistence.InvoicesEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Invoices}.
     */
    @Operation(
            method = "createInvoices",
            summary = "Create a new Invoices.",
            description = "This API is used to create a new Invoices in the system.",
            tags = {InvoicesApi.API_TAG},
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
                        description = "Successfully created a new Invoices in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/customers/{customerCustomerid}/invoiceses")
    public ResponseEntity<Invoices> createInvoices(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @Valid @RequestBody final CreateInvoicesRequest payload) {
        // Delegate to the service layer.
        final Invoices newInstance = invoicesService.createInvoices(customerCustomerid, payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.InvoicesEntity} in the system.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing Invoices, which needs to
     *     be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Invoices}.
     */
    @Operation(
            method = "updateInvoices",
            summary = "Update an existing Invoices.",
            description = "This API is used to update an existing Invoices in the system.",
            tags = {InvoicesApi.API_TAG},
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
                        description = "Successfully updated an existing Invoices in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/customers/{customerCustomerid}/invoiceses/{invoicesInvoiceid}")
    public ResponseEntity<Invoices> updateInvoices(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @PathVariable(name = "invoicesInvoiceid") final Integer invoicesInvoiceid,
            @Valid @RequestBody final UpdateInvoicesRequest payload) {
        // Delegate to the service layer.
        final Invoices updatedInstance =
                invoicesService.updateInvoices(customerCustomerid, invoicesInvoiceid, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.sampleapp.features.platform.data.model.persistence.InvoicesEntity} in the system.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, whose details have to
     *     be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Invoices}.
     */
    @Operation(
            method = "findInvoices",
            summary = "Find an existing Invoices.",
            description = "This API is used to find an existing Invoices in the system.",
            tags = {InvoicesApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Invoices in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/customers/{customerCustomerid}/invoiceses/{invoicesInvoiceid}")
    public ResponseEntity<Invoices> findInvoices(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @PathVariable(name = "invoicesInvoiceid") final Integer invoicesInvoiceid) {
        // Delegate to the service layer.
        final Invoices matchingInstance =
                invoicesService.findInvoices(customerCustomerid, invoicesInvoiceid);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.sampleapp.features.platform.data.model.persistence.InvoicesEntity} in the system in a
     * paginated manner.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Invoices based on the provided pagination settings.
     */
    @Operation(
            method = "findAllInvoiceses",
            summary = "Find all Invoiceses.",
            description = "This API is used to find all Invoiceses in the system.",
            tags = {InvoicesApi.API_TAG},
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
                                "Successfully retrieved the Invoiceses in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/customers/{customerCustomerid}/invoiceses")
    public ResponseEntity<Page<Invoices>> findAllInvoiceses(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Invoices> matchingInstances =
                invoicesService.findAllInvoiceses(customerCustomerid, pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.InvoicesEntity} in the system.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, which needs to be
     *     deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.sampleapp.features.platform.data.model.persistence.InvoicesEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteInvoices",
            summary = "Delete an existing Invoices.",
            description = "This API is used to delete an existing Invoices in the system.",
            tags = {InvoicesApi.API_TAG},
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
                        description = "Successfully deleted an existing Invoices in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/customers/{customerCustomerid}/invoiceses/{invoicesInvoiceid}")
    public ResponseEntity<Integer> deleteInvoices(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @PathVariable(name = "invoicesInvoiceid") final Integer invoicesInvoiceid) {
        // Delegate to the service layer.
        final Integer deletedInstance =
                invoicesService.deleteInvoices(customerCustomerid, invoicesInvoiceid);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
