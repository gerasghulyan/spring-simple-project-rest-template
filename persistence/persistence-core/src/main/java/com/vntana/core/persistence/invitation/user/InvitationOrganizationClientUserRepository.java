package com.vntana.core.persistence.invitation.user;

import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan
 * Date: 11/10/20
 * Time: 4:58 PM
 */
@Repository
public interface InvitationOrganizationClientUserRepository extends JpaRepository<InvitationOrganizationClientUser, Long> {

    Optional<InvitationOrganizationClientUser> findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);
}
