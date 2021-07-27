/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.model.experience.customer;

import java.math.BigDecimal;
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
public class Customer {
    /** Reference to the customerID. */
    private Integer customerID;

    /** Reference to the FirstName. */
    private String firstName;

    /** Reference to the LastName. */
    private String lastName;

    /** Reference to the Address. */
    private String address;

    /** Reference to the City. */
    private String city;

    /** Reference to the State. */
    private String state;

    /** Reference to the Zip. */
    private BigDecimal zip;

    /** Reference to the PhoneNumber. */
    private BigDecimal phoneNumber;
}
