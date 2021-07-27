/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.mapper;

import com.sampleapp.features.platform.data.mapper.decorator.InvoicesMapperDecorator;
import com.sampleapp.features.platform.data.model.experience.invoices.CreateInvoicesRequest;
import com.sampleapp.features.platform.data.model.experience.invoices.Invoices;
import com.sampleapp.features.platform.data.model.experience.invoices.UpdateInvoicesRequest;
import com.sampleapp.features.platform.data.model.persistence.InvoicesEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link InvoicesEntity to {@link Invoices and vice-versa.
 *
 * @author Ravindra Engle
 */
@DecoratedWith(value = InvoicesMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface InvoicesMapper {

    /**
     * This method transforms an instance of type {@link CreateInvoicesRequest} to an instance of
     * type {@link InvoicesEntity}.
     *
     * @param source Instance of type {@link CreateInvoicesRequest} that needs to be transformed to
     *     {@link InvoicesEntity}.
     * @return Instance of type {@link InvoicesEntity}.
     */
    @Mapping(source = "customerID", target = "customerID", ignore = true)
    InvoicesEntity transform(CreateInvoicesRequest source);

    /**
     * This method transforms an instance of type {@link InvoicesEntity} to an instance of type
     * {@link Invoices}.
     *
     * @param source Instance of type {@link InvoicesEntity} that needs to be transformed to {@link
     *     Invoices}.
     * @return Instance of type {@link Invoices}.
     */
    @Mapping(source = "customerID", target = "customerID", ignore = true)
    Invoices transform(InvoicesEntity source);

    /**
     * This method converts / transforms the provided collection of {@link InvoicesEntity} instances
     * to a collection of instances of type {@link Invoices}.
     *
     * @param source Instance of type {@link InvoicesEntity} that needs to be transformed to {@link
     *     Invoices}.
     * @return Collection of instance of type {@link Invoices}.
     */
    default Collection<Invoices> transformListTo(Collection<InvoicesEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateInvoicesRequest} to an instance of
     * type {@link InvoicesEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateInvoicesRequest} that needs to be transformed to
     *     {@link InvoicesEntity}.
     * @param target Instance of type {@link InvoicesEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    @Mapping(source = "customerID", target = "customerID", ignore = true)
    void transform(UpdateInvoicesRequest source, @MappingTarget InvoicesEntity target);

    /**
     * This method transforms an instance of type {@link UpdateInvoicesRequest} to an instance of
     * type {@link InvoicesEntity}.
     *
     * @param source Instance of type {@link UpdateInvoicesRequest} that needs to be transformed to
     *     {@link InvoicesEntity}.
     * @return Instance of type {@link InvoicesEntity}.
     */
    @Mapping(source = "customerID", target = "customerID", ignore = true)
    InvoicesEntity transform(UpdateInvoicesRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateInvoicesRequest}
     * instances to a collection of instances of type {@link InvoicesEntity}.
     *
     * @param source Instance of type {@link UpdateInvoicesRequest} that needs to be transformed to
     *     {@link InvoicesEntity}.
     * @return Instance of type {@link InvoicesEntity}.
     */
    default Collection<InvoicesEntity> transformList(Collection<UpdateInvoicesRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
