package com.vntana.core.service.invitation.user;

import com.vntana.core.domain.invitation.user.InvitationUser;
import com.vntana.core.service.invitation.user.dto.CreateInvitationUserDto;
import com.vntana.core.service.invitation.user.dto.GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto;
import com.vntana.core.service.invitation.user.dto.GetAllByOrganizationUuidAndStatusInvitationUsersDto;
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:06 PM
 */
public interface InvitationUserService {

    InvitationUser create(final CreateInvitationUserDto dto);

    boolean existsByUuid(final String uuid);

    InvitationUser getByUuid(final String uuid);

    Page<InvitationUser> getAllByOrganizationUuidAndStatus(final GetAllByOrganizationUuidAndStatusInvitationUsersDto dto);

    List<InvitationUser> getAllByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(final GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto dto);

    List<InvitationUser> getAllByInviterUserUuid(final String userUuid);

    InvitationUser updateStatus(final UpdateInvitationUserStatusDto dto);

    InvitationUser getByToken(final String token);

    boolean existsByToken(final String token);
}