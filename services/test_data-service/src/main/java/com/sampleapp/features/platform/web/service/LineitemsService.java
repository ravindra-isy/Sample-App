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
import com.sampleapp.features.platform.data.mapper.LineitemsMapper;
import com.sampleapp.features.platform.data.model.experience.lineitems.CreateLineitemsRequest;
import com.sampleapp.features.platform.data.model.experience.lineitems.Lineitems;
import com.sampleapp.features.platform.data.model.experience.lineitems.UpdateLineitemsRequest;
import com.sampleapp.features.platform.data.model.persistence.CustomerEntity;
import com.sampleapp.features.platform.data.model.persistence.InvoicesEntity;
import com.sampleapp.features.platform.data.model.persistence.LineitemsEntity;
import com.sampleapp.features.platform.data.repository.CustomerRepository;
import com.sampleapp.features.platform.data.repository.InvoicesRepository;
import com.sampleapp.features.platform.data.repository.LineitemsRepository;
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
 * entities of type {@link LineitemsEntity}.
 *
 * @author Ravindra Engle
 */
@Slf4j
@Validated
@Service
public class LineitemsService {

    /** Repository implementation of type {@link LineitemsRepository}. */
    private final LineitemsRepository lineitemsRepository;

    /**
     * Mapper implementation of type {@link LineitemsMapper} to transform between different types.
     */
    private final LineitemsMapper lineitemsMapper;

    /** Repository implementation of type {@link CustomerRepository}. */
    private final CustomerRepository customerRepository;

    /** Repository implementation of type {@link InvoicesRepository}. */
    private final InvoicesRepository invoicesRepository;

    /**
     * Constructor.
     *
     * @param lineitemsRepository Repository instance of type {@link LineitemsRepository}.
     * @param lineitemsMapper Mapper instance of type {@link LineitemsMapper}.
     * @param customerRepository Repository instance of type {@link CustomerRepository}.
     * @param invoicesRepository Repository instance of type {@link InvoicesRepository}.
     */
    public LineitemsService(
            final LineitemsRepository lineitemsRepository,
            final LineitemsMapper lineitemsMapper,
            final CustomerRepository customerRepository,
            final InvoicesRepository invoicesRepository) {
        this.lineitemsRepository = lineitemsRepository;
        this.lineitemsMapper = lineitemsMapper;
        this.customerRepository = customerRepository;
        this.invoicesRepository = invoicesRepository;
    }

    /**
     * This method attempts to create an instance of type {@link LineitemsEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     LineitemsEntity}.
     * @return An experience model of type {@link Lineitems} that represents the newly created
     *     entity of type {@link LineitemsEntity}.
     */
    @Instrument
    @Transactional
    public Lineitems createLineitems(
            final Integer customerCustomerid,
            final Integer invoicesInvoiceid,
            @Valid final CreateLineitemsRequest payload) {
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

        // 2.Transform the experience model to a persistence model.
        final LineitemsEntity lineitemsEntity = lineitemsMapper.transform(payload);

        // 3.Set the  parent persistence model to current persistence model.
        lineitemsEntity.setInvoiceID(matchingInvoices);

        // 4. Save the entity.
        final LineitemsEntity savedInstance = lineitemsRepository.save(lineitemsEntity);

        // 5. Transform and return.
        return lineitemsMapper.transform(savedInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link LineitemsEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateLineitemsRequest}.
     *
     * @param lineitemsLineid Unique identifier of Lineitems in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing Lineitems, which needs
     *     to be updated in the system.
     * @return A instance of type {@link Lineitems} containing the updated details.
     */
    @Instrument
    @Transactional
    public Lineitems updateLineitems(
            final Integer customerCustomerid,
            final Integer invoicesInvoiceid,
            final Integer lineitemsLineid,
            @Valid final UpdateLineitemsRequest payload) {
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

        final LineitemsEntity matchingLineitems =
                lineitemsRepository
                        .findById(lineitemsLineid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                lineitemsLineid));

        if (!matchingLineitems.getInvoiceID().equals(matchingInvoices)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2. Transform the experience model to a persistence model.
        lineitemsMapper.transform(payload, matchingLineitems);

        // 3. Save the entity.
        final LineitemsEntity updatedInstance = lineitemsRepository.save(matchingLineitems);

        // 4. Transform and return.
        return lineitemsMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link LineitemsEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param lineitemsLineid Unique identifier of Lineitems in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link Lineitems} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Lineitems findLineitems(
            final Integer customerCustomerid,
            final Integer invoicesInvoiceid,
            final Integer lineitemsLineid) {

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

        final LineitemsEntity matchingLineitems =
                lineitemsRepository
                        .findById(lineitemsLineid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                lineitemsLineid));

        if (!matchingLineitems.getInvoiceID().equals(matchingInvoices)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2. Transform and return.
        return lineitemsMapper.transform(matchingLineitems);
    }

    /**
     * This method attempts to find instances of type LineitemsEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Lineitems}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Lineitems> findAllLineitemses(
            final Integer customerCustomerid,
            final Integer invoicesInvoiceid,
            final Pageable page) {
        // 0. Validate the dependencies.

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

        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);

        LineitemsService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<LineitemsEntity> pageData = lineitemsRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Lineitems> dataToReturn =
                    pageData.getContent().stream()
                            .map(lineitemsMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // 4. Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }
    /**
     * This method attempts to delete an existing instance of type {@link LineitemsEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param lineitemsLineid Unique identifier of Lineitems in the system, which needs to be
     *     deleted.
     * @return Unique identifier of the instance of type LineitemsEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteLineitems(
            final Integer customerCustomerid,
            final Integer invoicesInvoiceid,
            final Integer lineitemsLineid) {
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

        final LineitemsEntity matchingLineitems =
                lineitemsRepository
                        .findById(lineitemsLineid)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID,
                                                lineitemsLineid));

        if (!matchingLineitems.getInvoiceID().equals(matchingInvoices)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2.Delete Persistence Entity
        lineitemsRepository.deleteById(lineitemsLineid);

        // 3. Return the deleted identifier
        return lineitemsLineid;
    }
}
