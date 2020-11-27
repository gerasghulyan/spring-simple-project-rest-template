package com.vntana.core.persistence.invitation.user;

import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select invitationUser from InvitationOrganizationClientUser invitationUser where invitationUser.id in (select tis.userInvitation.id from TokenUserInvitationToOrganizationClient tis where tis.token = :token)")
    Optional<InvitationOrganizationClientUser> findUserInvitationByToken(@Param("token") String token);

    Page<InvitationOrganizationClientUser> findAllByClientOrganizationUuidAndStatus(final String clientUuid, final InvitationStatus status, final Pageable pageable);
}
