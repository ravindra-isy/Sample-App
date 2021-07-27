/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.web.service;

import com.sampleapp.commons.data.utils.PageUtils;
import com.sampleapp.commons.exception.ServiceException;
import com.sampleapp.commons.instrumentation.Instrument;
import com.sampleapp.error.Errors;
import com.sampleapp.features.platform.data.mapper.InvoicesMapper;
import com.sampleapp.features.platform.data.model.experience.invoices.CreateInvoicesRequest;
import com.sampleapp.features.platform.data.model.experience.invoices.Invoices;
import com.sampleapp.features.platform.data.model.experience.invoices.UpdateInvoicesRequest;
import com.sampleapp.features.platform.data.model.persistence.CustomerEntity;
import com.sampleapp.features.platform.data.model.persistence.InvoicesEntity;
import com.sampleapp.features.platform.data.repository.CustomerRepository;
import com.sampleapp.features.platform.data.repository.InvoicesRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link InvoicesEntity}.
 *
 * @author Ravindra Engle
 */
@Slf4j
@Validated
@Service
public class InvoicesService {

    /** Repository implementation of type {@link InvoicesRepository}. */
    private final InvoicesRepository invoicesRepository;

    /**
     * Mapper implementation of type {@link InvoicesMapper} to transform between different types.
     */
    private final InvoicesMapper invoicesMapper;

    /** Repository implementation of type {@link CustomerRepository}. */
    private final CustomerRepository customerRepository;

    /**
     * Constructor.
     *
     * @param invoicesRepository Repository instance of type {@link InvoicesRepository}.
     * @param invoicesMapper Mapper instance of type {@link InvoicesMapper}.
     * @param customerRepository Repository instance of type {@link CustomerRepository}.
     */
    public InvoicesService(
            final InvoicesRepository invoicesRepository,
            final InvoicesMapper invoicesMapper,
            final CustomerRepository customerRepository) {
        this.invoicesRepository = invoicesRepository;
        this.invoicesMapper = invoicesMapper;
        this.customerRepository = customerRepository;
    }

    /**
     * This method attempts to create an instance of type {@link InvoicesEntity} in the system based
     * on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     InvoicesEntity}.
     * @return An experience model of type {@link Invoices} that represents the newly created entity
     *     of type {@link InvoicesEntity}.
     */
    @Instrument
    @Transactional
    public Invoices createInvoices(
            final Integer customerCustomerid, @Valid final CreateInvoicesRequest payload) {
        // 1. Validate the dependencies.

        final CustomerEntity matchingCustomer =
                customerRepository
                        .findById(customerCustomerid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                customerCustomerid));

        // 2.Transform the experience model to a persistence model.
        final InvoicesEntity invoicesEntity = invoicesMapper.transform(payload);

        // 3.Set the  parent persistence model to current persistence model.
        invoicesEntity.setCustomerID(matchingCustomer);

        // 4. Save the entity.
        final InvoicesEntity savedInstance = invoicesRepository.save(invoicesEntity);

        // 5. Transform and return.
        return invoicesMapper.transform(savedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link InvoicesEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateInvoicesRequest}.
     *
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing Invoices, which needs to
     *     be updated in the system.
     * @return A instance of type {@link Invoices} containing the updated details.
     */
    @Instrument
    @Transactional
    public Invoices updateInvoices(
            final Integer customerCustomerid,
            final Integer invoicesInvoiceid,
            @Valid final UpdateInvoicesRequest payload) {
        // 1. Validate the dependencies.

        final CustomerEntity matchingCustomer =
                customerRepository
                        .findById(customerCustomerid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                customerCustomerid));

        final InvoicesEntity matchingInvoices =
                invoicesRepository
                        .findById(invoicesInvoiceid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                invoicesInvoiceid));

        if (!matchingInvoices.getCustomerID().equals(matchingCustomer)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2. Transform the experience model to a persistence model.
        invoicesMapper.transform(payload, matchingInvoices);

        // 3. Save the entity.
        final InvoicesEntity updatedInstance = invoicesRepository.save(matchingInvoices);

        // 4. Transform and return.
        return invoicesMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link InvoicesEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, whose details have to
     *     be retrieved.
     * @return Matching entity of type {@link Invoices} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Invoices findInvoices(
            final Integer customerCustomerid, final Integer invoicesInvoiceid) {

        // 1. Validate the dependencies.

        final CustomerEntity matchingCustomer =
                customerRepository
                        .findById(customerCustomerid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                customerCustomerid));

        final InvoicesEntity matchingInvoices =
                invoicesRepository
                        .findById(invoicesInvoiceid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                invoicesInvoiceid));

        if (!matchingInvoices.getCustomerID().equals(matchingCustomer)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2. Transform and return.
        return invoicesMapper.transform(matchingInvoices);
    }

    /**
     * This method attempts to find instances of type InvoicesEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Invoices}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Invoices> findAllInvoiceses(final Integer customerCustomerid, final Pageable page) {
        // 0. Validate the dependencies.

        final CustomerEntity matchingCustomer =
                customerRepository
                        .findById(customerCustomerid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                customerCustomerid));

        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);

        InvoicesService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<InvoicesEntity> pageData = invoicesRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Invoices> dataToReturn =
                    pageData.getContent().stream()
                            .map(invoicesMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // 4. Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }
    /**
     * This method attempts to delete an existing instance of type {@link InvoicesEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param invoicesInvoiceid Unique identifier of Invoices in the system, which needs to be
     *     deleted.
     * @return Unique identifier of the instance of type InvoicesEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteInvoices(
            final Integer customerCustomerid, final Integer invoicesInvoiceid) {
        // 1. Validate the dependencies.

        final CustomerEntity matchingCustomer =
                customerRepository
                        .findById(customerCustomerid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                customerCustomerid));

        final InvoicesEntity matchingInvoices =
                invoicesRepository
                        .findById(invoicesInvoiceid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                invoicesInvoiceid));

        if (!matchingInvoices.getCustomerID().equals(matchingCustomer)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2.Delete Persistence Entity
        invoicesRepository.deleteById(invoicesInvoiceid);

        // 3. Return the deleted identifier
        return invoicesInvoiceid;
    }
}
