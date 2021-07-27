/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Sample-App.
 *
 * Sample-App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.sampleapp.commons.web.model.experience;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A data transfer object that is used to return the authentication response and typically contains the Bearer token
 * details.
 *
 * @author Ravindra Engle
 */
@ToString(of = {"accessToken"})
@EqualsAndHashCode(of = {"accessToken"})
@Data
@Builder
public class AuthenticationResponse {
    /** Access token. */
    private String accessToken;
}
