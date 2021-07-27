/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.model.persistence;

import com.sampleapp.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Implementation that maps the "lineitems" table in the database to an entity in the ORM world.
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
@Table(name = "lineitems")
@Entity
public class LineitemsEntity extends AbstractIdGeneratedEntity<Integer> {

    /** Reference to the productID. */
    @Column(name = "productID", nullable = false)
    private Integer productID;

    /** Reference to the Qualtity. */
    @Column(name = "Qualtity", nullable = false)
    private Integer qualtity;

    /** Reference to the invoiceID. */
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "invoiceID", referencedColumnName = "invoiceID", nullable = false)
    private InvoicesEntity invoiceID;
}
