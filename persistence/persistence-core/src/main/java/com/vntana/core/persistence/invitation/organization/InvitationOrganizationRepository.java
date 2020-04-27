package com.vntana.core.persistence.invitation.organization;

import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 3:48 PM
 */
public interface InvitationOrganizationRepository extends JpaRepository<InvitationOrganization, Long> {

    Optional<InvitationOrganization> findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);
}
