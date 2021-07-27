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
import com.sampleapp.features.platform.data.model.experience.lineitems.CreateLineitemsRequest;
import com.sampleapp.features.platform.data.model.experience.lineitems.Lineitems;
import com.sampleapp.features.platform.data.model.experience.lineitems.UpdateLineitemsRequest;
import com.sampleapp.features.platform.web.service.LineitemsService;
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
 * com.sampleapp.features.platform.data.model.persistence.LineitemsEntity}.
 *
 * @author Ravindra Engle
 */
@Slf4j
@RestController
public class LineitemsApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Lineitemses";

    /** Service implementation of type {@link LineitemsService}. */
    private final LineitemsService lineitemsService;

    /**
     * Constructor.
     *
     * @param lineitemsService Service instance of type {@link LineitemsService}.
     */
    public LineitemsApi(final LineitemsService lineitemsService) {
        this.lineitemsService = lineitemsService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.LineitemsEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.sampleapp.features.platform.data.model.persistence.LineitemsEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Lineitems}.
     */
    @Operation(
            method = "createLineitems",
            summary = "Create a new Lineitems.",
            description = "This API is used to create a new Lineitems in the system.",
            tags = {LineitemsApi.API_TAG},
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
                        description = "Successfully created a new Lineitems in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(
            value = "/customers/{customerCustomerid}/invoiceses/{invoicesInvoiceid}/lineitemses")
    public ResponseEntity<Lineitems> createLineitems(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @PathVariable(name = "invoicesInvoiceid") final Integer invoicesInvoiceid,
            @Valid @RequestBody final CreateLineitemsRequest payload) {
        // Delegate to the service layer.
        final Lineitems newInstance =
                lineitemsService.createLineitems(customerCustomerid, invoicesInvoiceid, payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.LineitemsEntity} in the system.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, whose details have to
     *     be retrieved.
     * @param lineitemsLineid Unique identifier of Lineitems in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing Lineitems, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Lineitems}.
     */
    @Operation(
            method = "updateLineitems",
            summary = "Update an existing Lineitems.",
            description = "This API is used to update an existing Lineitems in the system.",
            tags = {LineitemsApi.API_TAG},
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
                        description = "Successfully updated an existing Lineitems in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(
            value =
                    "/customers/{customerCustomerid}/invoiceses/{invoicesInvoiceid}/lineitemses/{lineitemsLineid}")
    public ResponseEntity<Lineitems> updateLineitems(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @PathVariable(name = "invoicesInvoiceid") final Integer invoicesInvoiceid,
            @PathVariable(name = "lineitemsLineid") final Integer lineitemsLineid,
            @Valid @RequestBody final UpdateLineitemsRequest payload) {
        // Delegate to the service layer.
        final Lineitems updatedInstance =
                lineitemsService.updateLineitems(
                        customerCustomerid, invoicesInvoiceid, lineitemsLineid, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.sampleapp.features.platform.data.model.persistence.LineitemsEntity} in the system.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, whose details have to
     *     be retrieved.
     * @param lineitemsLineid Unique identifier of Lineitems in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Lineitems}.
     */
    @Operation(
            method = "findLineitems",
            summary = "Find an existing Lineitems.",
            description = "This API is used to find an existing Lineitems in the system.",
            tags = {LineitemsApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Lineitems in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(
            value =
                    "/customers/{customerCustomerid}/invoiceses/{invoicesInvoiceid}/lineitemses/{lineitemsLineid}")
    public ResponseEntity<Lineitems> findLineitems(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @PathVariable(name = "invoicesInvoiceid") final Integer invoicesInvoiceid,
            @PathVariable(name = "lineitemsLineid") final Integer lineitemsLineid) {
        // Delegate to the service layer.
        final Lineitems matchingInstance =
                lineitemsService.findLineitems(
                        customerCustomerid, invoicesInvoiceid, lineitemsLineid);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.sampleapp.features.platform.data.model.persistence.LineitemsEntity} in the system in a
     * paginated manner.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, whose details have to
     *     be retrieved.
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Lineitems based on the provided pagination settings.
     */
    @Operation(
            method = "findAllLineitemses",
            summary = "Find all Lineitemses.",
            description = "This API is used to find all Lineitemses in the system.",
            tags = {LineitemsApi.API_TAG},
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
                                "Successfully retrieved the Lineitemses in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(
            value = "/customers/{customerCustomerid}/invoiceses/{invoicesInvoiceid}/lineitemses")
    public ResponseEntity<Page<Lineitems>> findAllLineitemses(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @PathVariable(name = "invoicesInvoiceid") final Integer invoicesInvoiceid,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Lineitems> matchingInstances =
                lineitemsService.findAllLineitemses(
                        customerCustomerid, invoicesInvoiceid, pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.sampleapp.features.platform.data.model.persistence.LineitemsEntity} in the system.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, whose details have to
     *     be retrieved.
     * @param lineitemsLineid Unique identifier of Lineitems in the system, which needs to be
     *     deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.sampleapp.features.platform.data.model.persistence.LineitemsEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteLineitems",
            summary = "Delete an existing Lineitems.",
            description = "This API is used to delete an existing Lineitems in the system.",
            tags = {LineitemsApi.API_TAG},
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
                        description = "Successfully deleted an existing Lineitems in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(
            value =
                    "/customers/{customerCustomerid}/invoiceses/{invoicesInvoiceid}/lineitemses/{lineitemsLineid}")
    public ResponseEntity<Integer> deleteLineitems(
            @PathVariable(name = "customerCustomerid") final Integer customerCustomerid,
            @PathVariable(name = "invoicesInvoiceid") final Integer invoicesInvoiceid,
            @PathVariable(name = "lineitemsLineid") final Integer lineitemsLineid) {
        // Delegate to the service layer.
        final Integer deletedInstance =
                lineitemsService.deleteLineitems(
                        customerCustomerid, invoicesInvoiceid, lineitemsLineid);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
