/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is
 * confidential and proprietary information of Innominds inc. You shall not disclose
 * Confidential Information and shall use it only in accordance with the terms
 *
 */
package com.sampleapp.features.platform.data.mapper;

import com.sampleapp.features.platform.data.model.experience.role.CreateRoleRequest;
import com.sampleapp.features.platform.data.model.experience.role.Role;
import com.sampleapp.features.platform.data.model.experience.role.UpdateRoleRequest;
import com.sampleapp.features.platform.data.model.persistence.RoleEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link RoleEntity to {@link Role and vice-versa.
 *
 * @author Ravindra Engle
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RoleMapper {

    /**
     * This method transforms an instance of type {@link CreateRoleRequest} to an instance of type
     * {@link RoleEntity}.
     *
     * @param source Instance of type {@link CreateRoleRequest} that needs to be transformed to
     *     {@link RoleEntity}.
     * @return Instance of type {@link RoleEntity}.
     */
    RoleEntity transform(CreateRoleRequest source);

    /**
     * This method transforms an instance of type {@link RoleEntity} to an instance of type {@link
     * Role}.
     *
     * @param source Instance of type {@link RoleEntity} that needs to be transformed to {@link
     *     Role}.
     * @return Instance of type {@link Role}.
     */
    Role transform(RoleEntity source);

    /**
     * This method converts / transforms the provided collection of {@link RoleEntity} instances to
     * a collection of instances of type {@link Role}.
     *
     * @param source Instance of type {@link RoleEntity} that needs to be transformed to {@link
     *     Role}.
     * @return Collection of instance of type {@link Role}.
     */
    default Collection<Role> transformListTo(Collection<RoleEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateRoleRequest} to an instance of type
     * {@link RoleEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateRoleRequest} that needs to be transformed to
     *     {@link RoleEntity}.
     * @param target Instance of type {@link RoleEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    void transform(UpdateRoleRequest source, @MappingTarget RoleEntity target);

    /**
     * This method transforms an instance of type {@link UpdateRoleRequest} to an instance of type
     * {@link RoleEntity}.
     *
     * @param source Instance of type {@link UpdateRoleRequest} that needs to be transformed to
     *     {@link RoleEntity}.
     * @return Instance of type {@link RoleEntity}.
     */
    RoleEntity transform(UpdateRoleRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateRoleRequest}
     * instances to a collection of instances of type {@link RoleEntity}.
     *
     * @param source Instance of type {@link UpdateRoleRequest} that needs to be transformed to
     *     {@link RoleEntity}.
     * @return Instance of type {@link RoleEntity}.
     */
    default Collection<RoleEntity> transformList(Collection<UpdateRoleRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
