package com.vntana.core.service.invitation.user;

import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import com.vntana.core.service.invitation.user.dto.CreateInvitationForOrganizationUserDto;
import com.vntana.core.service.invitation.user.dto.GetAllByOrganizationUuidAndStatusInvitationUsersDto;
import com.vntana.core.service.invitation.user.dto.GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto;
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:06 PM
 */
public interface InvitationUserToOrganizationService {

    InvitationOrganizationUser create(final CreateInvitationForOrganizationUserDto dto);
    
    boolean existsByUuid(final String uuid);

    InvitationOrganizationUser getByUuid(final String uuid);

    Page<InvitationOrganizationUser> getAllByOrganizationUuidAndStatus(final GetAllByOrganizationUuidAndStatusInvitationUsersDto dto);

    List<InvitationOrganizationUser> getAllByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(final GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto dto);

    List<InvitationOrganizationUser> getAllByInviterUserUuid(final String userUuid);

    InvitationOrganizationUser updateStatus(final UpdateInvitationUserStatusDto dto);

    InvitationOrganizationUser getByToken(final String token);

    boolean existsByToken(final String token);
}