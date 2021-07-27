/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.mapper.decorator;

import com.sampleapp.features.platform.data.mapper.InvoicesMapper;
import com.sampleapp.features.platform.data.mapper.LineitemsMapper;
import com.sampleapp.features.platform.data.model.experience.lineitems.CreateLineitemsRequest;
import com.sampleapp.features.platform.data.model.experience.lineitems.Lineitems;
import com.sampleapp.features.platform.data.model.experience.lineitems.UpdateLineitemsRequest;
import com.sampleapp.features.platform.data.model.persistence.LineitemsEntity;
import com.sampleapp.features.platform.data.repository.InvoicesRepository;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link LineitemsEntity to {@link Lineitems and vice-versa.
 *
 * @author Ravindra Engle
 */
@Slf4j
public abstract class LineitemsMapperDecorator implements LineitemsMapper {

    /** Mapper implementation of type {@link LineitemsMapper}. */
    @Autowired private LineitemsMapper lineitemsMapper;

    /** Repository implementation of type {@link InvoicesRepository}. */
    @Autowired private InvoicesRepository invoicesRepository;

    /** Mapper implementation of type {@link InvoicesMapper}. */
    @Autowired private InvoicesMapper invoicesMapper;

    @Override
    public LineitemsEntity transform(final CreateLineitemsRequest source) {
        // 1. Transform the CreateLineitemsRequest to LineitemsEntity object.
        final LineitemsEntity lineitems = lineitemsMapper.transform(source);

        if (Objects.nonNull(source.getInvoiceID())) {
            lineitems.setInvoiceID(invoicesRepository.findByIdOrThrow(source.getInvoiceID()));
        }
        // Return the transformed object.
        return lineitems;
    }

    @Override
    public Lineitems transform(final LineitemsEntity source) {
        // 1. Transform the LineitemsEntity to Lineitems object.
        final Lineitems lineitems = lineitemsMapper.transform(source);

        if (Objects.nonNull(source.getInvoiceID())) {
            lineitems.setInvoiceID(invoicesMapper.transform(source.getInvoiceID()));
        }
        // Return the transformed object.
        return lineitems;
    }

    @Override
    public void transform(
            final UpdateLineitemsRequest source, final @MappingTarget LineitemsEntity target) {

        // Transform from source to the target.
        lineitemsMapper.transform(source, target);

        if (Objects.nonNull(source.getInvoiceID())) {
            target.setInvoiceID(invoicesRepository.findByIdOrThrow(source.getInvoiceID()));
        }
    }

    @Override
    public LineitemsEntity transform(final UpdateLineitemsRequest source) {

        // Transform from source to the target.
        final LineitemsEntity lineitems = lineitemsMapper.transform(source);

        if (Objects.nonNull(source.getInvoiceID())) {
            lineitems.setInvoiceID(invoicesRepository.findByIdOrThrow(source.getInvoiceID()));
        }
        // Return the response.
        return lineitems;
    }
}
