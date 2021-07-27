/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.model.experience.invoices;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Implementation of an experience model that is meant to be used by the API Layer for communication
 * either with the front-end or to the service-layer.
 *
 * @author Ravindra Engle
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class UpdateInvoicesRequest {
    /** Reference to the invoiceID. */
    @NotNull(message = "invoices.invoiceID.not.null.message")
    private Integer invoiceID;

    /** Reference to the customerID. */
    @NotNull(message = "invoices.customerID.not.null.message")
    private Integer customerID;
}
