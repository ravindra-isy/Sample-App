/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.model.experience.customer;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class CreateCustomerRequest {
    /** Reference to the FirstName. */
    @NotBlank(message = "customer.firstName.not.blank.message")
    @Size(max = 20, message = "customer.firstName.size.message")
    private String firstName;

    /** Reference to the LastName. */
    @NotBlank(message = "customer.lastName.not.blank.message")
    @Size(max = 20, message = "customer.lastName.size.message")
    private String lastName;

    /** Reference to the Address. */
    @NotBlank(message = "customer.address.not.blank.message")
    @Size(max = 20, message = "customer.address.size.message")
    private String address;

    /** Reference to the City. */
    @NotBlank(message = "customer.city.not.blank.message")
    @Size(max = 20, message = "customer.city.size.message")
    private String city;

    /** Reference to the State. */
    @NotBlank(message = "customer.state.not.blank.message")
    @Size(max = 20, message = "customer.state.size.message")
    private String state;

    /** Reference to the Zip. */
    @NotNull(message = "customer.zip.not.null.message")
    @Min(value = 10, message = "customer.zip.min.message")
    @Max(value = 20, message = "customer.zip.max.message")
    private BigDecimal zip;

    /** Reference to the PhoneNumber. */
    @NotNull(message = "customer.phoneNumber.not.null.message")
    @Min(value = 10, message = "customer.phoneNumber.min.message")
    @Max(value = 20, message = "customer.phoneNumber.max.message")
    private BigDecimal phoneNumber;
}
