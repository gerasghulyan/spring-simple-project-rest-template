package com.vntana.core.service.invitation.organization.dto;

import com.vntana.core.service.common.dto.AbstractPaginationAwareDto;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 4:34 PM
 */
public class GetAllInvitationOrganizationsDto extends AbstractPaginationAwareDto {

    public GetAllInvitationOrganizationsDto(final int size) {
        super(size);
    }

    public GetAllInvitationOrganizationsDto(final int page, final int size) {
        super(page, size);
    }
}
