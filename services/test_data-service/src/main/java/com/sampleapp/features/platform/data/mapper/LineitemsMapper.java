/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.mapper;

import com.sampleapp.features.platform.data.mapper.decorator.LineitemsMapperDecorator;
import com.sampleapp.features.platform.data.model.experience.lineitems.CreateLineitemsRequest;
import com.sampleapp.features.platform.data.model.experience.lineitems.Lineitems;
import com.sampleapp.features.platform.data.model.experience.lineitems.UpdateLineitemsRequest;
import com.sampleapp.features.platform.data.model.persistence.LineitemsEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link LineitemsEntity to {@link Lineitems and vice-versa.
 *
 * @author Ravindra Engle
 */
@DecoratedWith(value = LineitemsMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface LineitemsMapper {

    /**
     * This method transforms an instance of type {@link CreateLineitemsRequest} to an instance of
     * type {@link LineitemsEntity}.
     *
     * @param source Instance of type {@link CreateLineitemsRequest} that needs to be transformed to
     *     {@link LineitemsEntity}.
     * @return Instance of type {@link LineitemsEntity}.
     */
    @Mapping(source = "invoiceID", target = "invoiceID", ignore = true)
    LineitemsEntity transform(CreateLineitemsRequest source);

    /**
     * This method transforms an instance of type {@link LineitemsEntity} to an instance of type
     * {@link Lineitems}.
     *
     * @param source Instance of type {@link LineitemsEntity} that needs to be transformed to {@link
     *     Lineitems}.
     * @return Instance of type {@link Lineitems}.
     */
    @Mapping(source = "invoiceID", target = "invoiceID", ignore = true)
    Lineitems transform(LineitemsEntity source);

    /**
     * This method converts / transforms the provided collection of {@link LineitemsEntity}
     * instances to a collection of instances of type {@link Lineitems}.
     *
     * @param source Instance of type {@link LineitemsEntity} that needs to be transformed to {@link
     *     Lineitems}.
     * @return Collection of instance of type {@link Lineitems}.
     */
    default Collection<Lineitems> transformListTo(Collection<LineitemsEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateLineitemsRequest} to an instance of
     * type {@link LineitemsEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateLineitemsRequest} that needs to be transformed to
     *     {@link LineitemsEntity}.
     * @param target Instance of type {@link LineitemsEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    @Mapping(source = "invoiceID", target = "invoiceID", ignore = true)
    void transform(UpdateLineitemsRequest source, @MappingTarget LineitemsEntity target);

    /**
     * This method transforms an instance of type {@link UpdateLineitemsRequest} to an instance of
     * type {@link LineitemsEntity}.
     *
     * @param source Instance of type {@link UpdateLineitemsRequest} that needs to be transformed to
     *     {@link LineitemsEntity}.
     * @return Instance of type {@link LineitemsEntity}.
     */
    @Mapping(source = "invoiceID", target = "invoiceID", ignore = true)
    LineitemsEntity transform(UpdateLineitemsRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateLineitemsRequest}
     * instances to a collection of instances of type {@link LineitemsEntity}.
     *
     * @param source Instance of type {@link UpdateLineitemsRequest} that needs to be transformed to
     *     {@link LineitemsEntity}.
     * @return Instance of type {@link LineitemsEntity}.
     */
    default Collection<LineitemsEntity> transformList(Collection<UpdateLineitemsRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
