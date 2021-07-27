/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.repository;

import com.sampleapp.commons.data.jpa.repository.ExtendedJpaRepository;
import com.sampleapp.features.platform.data.model.persistence.InvoicesEntity;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to handle the operations pertaining to domain models of type
 * "InvoicesEntity".
 *
 * @author Ravindra Engle
 */
@Repository
public interface InvoicesRepository extends ExtendedJpaRepository<InvoicesEntity, Integer> {
    // Any custom methods can be added here.
}
