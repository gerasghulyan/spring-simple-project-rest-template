package com.vntana.core.service.invitation.user;

import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import com.vntana.core.service.invitation.user.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:06 PM
 */
public interface InvitationUserService {

    InvitationOrganizationUser createInvitationForOrganization(final CreateInvitationForOrganizationUserDto dto);
    
    List<InvitationOrganizationClientUser> creteInvitationForClients(final CreateInvitationForClientsUserDto dto);

    boolean existsByUuid(final String uuid);

    InvitationOrganizationUser getByUuid(final String uuid);

    Page<InvitationOrganizationUser> getAllByOrganizationUuidAndStatus(final GetAllByOrganizationUuidAndStatusInvitationUsersDto dto);

    List<InvitationOrganizationUser> getAllByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(final GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto dto);

    List<InvitationOrganizationUser> getAllByInviterUserUuid(final String userUuid);

    InvitationOrganizationUser updateStatus(final UpdateInvitationUserStatusDto dto);

    InvitationOrganizationUser getByToken(final String token);

    boolean existsByToken(final String token);
}