/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Sample-App.
 *
 * Sample-App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.sampleapp.commons.exception;

import com.sampleapp.commons.error.CommonErrors;

/**
 * Specialized exception implementation to handle the cases where a resource is not found.
 * <p>
 * This exception is generally used in the Service layer to report the cases where a resource is not found. For example:
 * find a specific user, get the details of a specific user, etc.
 *
 * @author Ravindra Engle
 */
public class ResourceNotFoundException extends ServiceException {
    /**
     * Constructor.
     */
    public ResourceNotFoundException() {
        super(CommonErrors.RESOURCE_NOT_FOUND);
    }
}
