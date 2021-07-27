/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Sample-App.
 *
 * Sample-App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.sampleapp.security.mfa;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.sampleapp.security.mfa.configuration.MFAConfiguration;

/**
 * A meta-annotation that configures all the beans required for the Multi-Factor Authentication functionality.
 * <p>
 * It is recommended to annotate the application's main class with this annotation.
 *
 * @author Ravindra Engle
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(value = {MFAConfiguration.class})
public @interface EnableMFA {
}
