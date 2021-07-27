/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.repository;

import com.sampleapp.commons.data.jpa.repository.ExtendedJpaRepository;
import com.sampleapp.features.platform.data.model.persistence.RoleEntity;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to handle the operations pertaining to domain models of type "RoleEntity".
 *
 * @author Ravindra Engle
 */
@Repository
public interface RoleRepository extends ExtendedJpaRepository<RoleEntity, String> {
    // Any custom methods can be added here.
}
