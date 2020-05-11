package com.vntana.core.service.invitation.user.dto;

import com.vntana.core.service.common.dto.AbstractPaginationAwareDto;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:32 PM
 */
public class GetAllInvitationUsersDto extends AbstractPaginationAwareDto {
    public GetAllInvitationUsersDto(final int size) {
        super(size);
    }

    public GetAllInvitationUsersDto(final int page, final int size) {
        super(page, size);
    }
}