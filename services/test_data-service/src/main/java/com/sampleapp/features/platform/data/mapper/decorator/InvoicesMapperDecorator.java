/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.mapper.decorator;

import com.sampleapp.features.platform.data.mapper.CustomerMapper;
import com.sampleapp.features.platform.data.mapper.InvoicesMapper;
import com.sampleapp.features.platform.data.model.experience.invoices.CreateInvoicesRequest;
import com.sampleapp.features.platform.data.model.experience.invoices.Invoices;
import com.sampleapp.features.platform.data.model.experience.invoices.UpdateInvoicesRequest;
import com.sampleapp.features.platform.data.model.persistence.InvoicesEntity;
import com.sampleapp.features.platform.data.repository.CustomerRepository;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link InvoicesEntity to {@link Invoices and vice-versa.
 *
 * @author Ravindra Engle
 */
@Slf4j
public abstract class InvoicesMapperDecorator implements InvoicesMapper {

    /** Mapper implementation of type {@link InvoicesMapper}. */
    @Autowired private InvoicesMapper invoicesMapper;

    /** Repository implementation of type {@link CustomerRepository}. */
    @Autowired private CustomerRepository customerRepository;

    /** Mapper implementation of type {@link CustomerMapper}. */
    @Autowired private CustomerMapper customerMapper;

    @Override
    public InvoicesEntity transform(final CreateInvoicesRequest source) {
        // 1. Transform the CreateInvoicesRequest to InvoicesEntity object.
        final InvoicesEntity invoices = invoicesMapper.transform(source);

        if (Objects.nonNull(source.getCustomerID())) {
            invoices.setCustomerID(customerRepository.findByIdOrThrow(source.getCustomerID()));
        }
        // Return the transformed object.
        return invoices;
    }

    @Override
    public Invoices transform(final InvoicesEntity source) {
        // 1. Transform the InvoicesEntity to Invoices object.
        final Invoices invoices = invoicesMapper.transform(source);

        if (Objects.nonNull(source.getCustomerID())) {
            invoices.setCustomerID(customerMapper.transform(source.getCustomerID()));
        }
        // Return the transformed object.
        return invoices;
    }

    @Override
    public void transform(
            final UpdateInvoicesRequest source, final @MappingTarget InvoicesEntity target) {

        // Transform from source to the target.
        invoicesMapper.transform(source, target);

        if (Objects.nonNull(source.getCustomerID())) {
            target.setCustomerID(customerRepository.findByIdOrThrow(source.getCustomerID()));
        }
    }

    @Override
    public InvoicesEntity transform(final UpdateInvoicesRequest source) {

        // Transform from source to the target.
        final InvoicesEntity invoices = invoicesMapper.transform(source);

        if (Objects.nonNull(source.getCustomerID())) {
            invoices.setCustomerID(customerRepository.findByIdOrThrow(source.getCustomerID()));
        }
        // Return the response.
        return invoices;
    }
}
