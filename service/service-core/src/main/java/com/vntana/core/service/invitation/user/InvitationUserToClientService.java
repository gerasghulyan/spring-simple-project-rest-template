package com.vntana.core.service.invitation.user;

import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import com.vntana.core.service.invitation.user.dto.CreateInvitationForClientsUserDto;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 12:30 PM
 */
public interface InvitationUserToClientService {

    List<InvitationOrganizationClientUser> create(final CreateInvitationForClientsUserDto dto);

    InvitationOrganizationClientUser getByUuid(final String uuid);

    boolean existsByUuid(final String uuid);
}
