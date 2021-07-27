/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of Sample-App.
 *
 * Sample-App project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.sampleapp.commons.data.jpa.persistence;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.sampleapp.commons.data.persistence.IEntity;

/**
 * Abstract implementation of an entity where the value of the primary key is auto-generated based on the supported
 * strategies.
 *
 * @param <ID>
 *         Type of the primary key.
 *
 * @author Ravindra Engle
 */
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractIdGeneratedEntity<ID extends Serializable> implements IEntity<ID> {
    /** Unique identifier of the entity - typically a primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private ID id;
}
