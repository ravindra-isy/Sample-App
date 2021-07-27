/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.model.experience.lineitems;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class CreateLineitemsRequest {
    /** Reference to the productID. */
    @NotNull(message = "lineitems.productID.not.null.message")
    @Min(value = 10, message = "lineitems.productID.min.message")
    @Max(value = 20, message = "lineitems.productID.max.message")
    private Integer productID;

    /** Reference to the Qualtity. */
    @NotNull(message = "lineitems.qualtity.not.null.message")
    @Min(value = 10, message = "lineitems.qualtity.min.message")
    @Max(value = 20, message = "lineitems.qualtity.max.message")
    private Integer qualtity;

    /** Reference to the invoiceID. */
    @NotNull(message = "lineitems.invoiceID.not.null.message")
    private Integer invoiceID;
}
