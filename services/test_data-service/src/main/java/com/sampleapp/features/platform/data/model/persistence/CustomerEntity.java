/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.model.persistence;

import com.sampleapp.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "customer" table in the database to an entity in the ORM world.
 *
 * @author Ravindra Engle
 */
@EqualsAndHashCode(
        callSuper = true,
        of = {})
@ToString(
        callSuper = true,
        of = {})
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customer")
@Entity
public class CustomerEntity extends AbstractIdGeneratedEntity<Integer> {

    /** Reference to the FirstName. */
    @Column(name = "FirstName", nullable = false, length = 20)
    private String firstName;

    /** Reference to the LastName. */
    @Column(name = "LastName", nullable = false, length = 20)
    private String lastName;

    /** Reference to the Address. */
    @Column(name = "Address", nullable = false, length = 20)
    private String address;

    /** Reference to the City. */
    @Column(name = "City", nullable = false, length = 20)
    private String city;

    /** Reference to the State. */
    @Column(name = "State", nullable = false, length = 20)
    private String state;

    /** Reference to the Zip. */
    @Column(name = "Zip", nullable = false, precision = 0, scale = 0)
    private BigDecimal zip;

    /** Reference to the PhoneNumber. */
    @Column(name = "PhoneNumber", nullable = false, precision = 0, scale = 0)
    private BigDecimal phoneNumber;
}
