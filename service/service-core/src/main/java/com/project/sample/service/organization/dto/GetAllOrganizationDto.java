package com.project.sample.service.organization.dto;

import com.project.sample.commons.service.dto.AbstractPaginationAwareDto;

/**
 * Created by Geras Ghulyan.
 * Date: 3/24/2020
 * Time: 2:58 PM
 */
public class GetAllOrganizationDto extends AbstractPaginationAwareDto {
    public GetAllOrganizationDto(final int size) {
        super(0, size);
    }

    public GetAllOrganizationDto(final int page, final int size) {
        super(page, size);
    }
}