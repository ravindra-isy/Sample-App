/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.mapper;

import com.sampleapp.features.platform.data.model.experience.customer.CreateCustomerRequest;
import com.sampleapp.features.platform.data.model.experience.customer.Customer;
import com.sampleapp.features.platform.data.model.experience.customer.UpdateCustomerRequest;
import com.sampleapp.features.platform.data.model.persistence.CustomerEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link CustomerEntity to {@link Customer and vice-versa.
 *
 * @author Ravindra Engle
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CustomerMapper {

    /**
     * This method transforms an instance of type {@link CreateCustomerRequest} to an instance of
     * type {@link CustomerEntity}.
     *
     * @param source Instance of type {@link CreateCustomerRequest} that needs to be transformed to
     *     {@link CustomerEntity}.
     * @return Instance of type {@link CustomerEntity}.
     */
    CustomerEntity transform(CreateCustomerRequest source);

    /**
     * This method transforms an instance of type {@link CustomerEntity} to an instance of type
     * {@link Customer}.
     *
     * @param source Instance of type {@link CustomerEntity} that needs to be transformed to {@link
     *     Customer}.
     * @return Instance of type {@link Customer}.
     */
    Customer transform(CustomerEntity source);

    /**
     * This method converts / transforms the provided collection of {@link CustomerEntity} instances
     * to a collection of instances of type {@link Customer}.
     *
     * @param source Instance of type {@link CustomerEntity} that needs to be transformed to {@link
     *     Customer}.
     * @return Collection of instance of type {@link Customer}.
     */
    default Collection<Customer> transformListTo(Collection<CustomerEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateCustomerRequest} to an instance of
     * type {@link CustomerEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateCustomerRequest} that needs to be transformed to
     *     {@link CustomerEntity}.
     * @param target Instance of type {@link CustomerEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    void transform(UpdateCustomerRequest source, @MappingTarget CustomerEntity target);

    /**
     * This method transforms an instance of type {@link UpdateCustomerRequest} to an instance of
     * type {@link CustomerEntity}.
     *
     * @param source Instance of type {@link UpdateCustomerRequest} that needs to be transformed to
     *     {@link CustomerEntity}.
     * @return Instance of type {@link CustomerEntity}.
     */
    CustomerEntity transform(UpdateCustomerRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateCustomerRequest}
     * instances to a collection of instances of type {@link CustomerEntity}.
     *
     * @param source Instance of type {@link UpdateCustomerRequest} that needs to be transformed to
     *     {@link CustomerEntity}.
     * @return Instance of type {@link CustomerEntity}.
     */
    default Collection<CustomerEntity> transformList(Collection<UpdateCustomerRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
