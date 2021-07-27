/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.web.service;

import com.sampleapp.commons.data.utils.PageUtils;
import com.sampleapp.commons.instrumentation.Instrument;
import com.sampleapp.features.platform.data.mapper.CustomerMapper;
import com.sampleapp.features.platform.data.model.experience.customer.CreateCustomerRequest;
import com.sampleapp.features.platform.data.model.experience.customer.Customer;
import com.sampleapp.features.platform.data.model.experience.customer.UpdateCustomerRequest;
import com.sampleapp.features.platform.data.model.persistence.CustomerEntity;
import com.sampleapp.features.platform.data.repository.CustomerRepository;
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
 * entities of type {@link CustomerEntity}.
 *
 * @author Ravindra Engle
 */
@Slf4j
@Validated
@Service
public class CustomerService {
    /** Repository implementation of type {@link CustomerRepository}. */
    private final CustomerRepository customerRepository;

    /**
     * Mapper implementation of type {@link CustomerMapper} to transform between different types.
     */
    private final CustomerMapper customerMapper;

    /**
     * Constructor.
     *
     * @param customerRepository Repository instance of type {@link CustomerRepository}.
     * @param customerMapper Mapper instance of type {@link CustomerMapper}.
     */
    public CustomerService(
            final CustomerRepository customerRepository, final CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * This method attempts to create an instance of type {@link CustomerEntity} in the system based
     * on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     CustomerEntity}.
     * @return An experience model of type {@link Customer} that represents the newly created entity
     *     of type {@link CustomerEntity}.
     */
    @Instrument
    @Transactional
    public Customer createCustomer(@Valid final CreateCustomerRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final CustomerEntity customerEntity = customerMapper.transform(payload);

        // 2. Save the entity.
        CustomerService.LOGGER.debug("Saving a new instance of type - CustomerEntity");
        final CustomerEntity newInstance = customerRepository.save(customerEntity);

        // 3. Transform the created entity to an experience model and return it.
        return customerMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link CustomerEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateCustomerRequest}.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing Customer, which needs to
     *     be updated in the system.
     * @return A instance of type {@link Customer} containing the updated details.
     */
    @Instrument
    @Transactional
    public Customer updateCustomer(
            final Integer customerCustomerid, @Valid final UpdateCustomerRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final CustomerEntity matchingInstance =
                customerRepository.findByIdOrThrow(customerCustomerid);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        customerMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        CustomerService.LOGGER.debug("Saving the updated entity - CustomerEntity");
        final CustomerEntity updatedInstance = customerRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return customerMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link CustomerEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, whose details have to
     *     be retrieved.
     * @return Matching entity of type {@link Customer} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Customer findCustomer(final Integer customerCustomerid) {
        // 1. Find a matching entity and throw an exception if not found.
        final CustomerEntity matchingInstance =
                customerRepository.findByIdOrThrow(customerCustomerid);

        // 2. Transform the matching entity to the desired output.
        return customerMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type CustomerEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Customer}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Customer> findAllCustomers(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        CustomerService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<CustomerEntity> pageData = customerRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Customer> dataToReturn =
                    pageData.getContent().stream()
                            .map(customerMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link CustomerEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param customerCustomerid Unique identifier of Customer in the system, which needs to be
     *     deleted.
     * @return Unique identifier of the instance of type CustomerEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteCustomer(final Integer customerCustomerid) {
        // 1. Delegate to our repository method to handle the deletion.
        return customerRepository.deleteOne(customerCustomerid);
    }
}
