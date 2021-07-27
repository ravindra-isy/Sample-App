/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.model.experience.lineitems;

import com.sampleapp.features.platform.data.model.experience.invoices.Invoices;
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
public class Lineitems {
    /** Reference to the lineID. */
    private Integer lineID;

    /** Reference to the productID. */
    private Integer productID;

    /** Reference to the Qualtity. */
    private Integer qualtity;

    /** Reference to the invoiceID. */
    private Invoices invoiceID;
}
