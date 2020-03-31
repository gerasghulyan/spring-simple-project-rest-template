package com.vntana.core.service.invitation.organization;

import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.service.invitation.organization.dto.CreateInvitationOrganizationDto;
import com.vntana.core.service.invitation.organization.dto.GetAllInvitationOrganizationsDto;
import org.springframework.data.domain.Page;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 3:15 PM
 */
public interface InvitationOrganizationService {

    InvitationOrganization create(final CreateInvitationOrganizationDto dto);

    InvitationOrganization getByUuid(final String uuid);

    boolean existsByUuid(final String uuid);

    Page<InvitationOrganization> getAll(final GetAllInvitationOrganizationsDto dto);
}
