package com.vntana.core.persistence.invitation.user;

import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:05 PM
 */
@Repository
public interface InvitationOrganizationUserRepository extends JpaRepository<InvitationOrganizationUser, Long> {

    Optional<InvitationOrganizationUser> findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);

    List<InvitationOrganizationUser> findByInviterUserUuid(final String uuid);

    List<InvitationOrganizationUser> findByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(final String email, final String organizationUuid, final InvitationStatus status);

    Page<InvitationOrganizationUser> findAllByOrganizationUuidAndStatus(final String organizationUuid, final InvitationStatus status, final Pageable pageable);

    @Query("select invitationUser from InvitationOrganizationUser invitationUser where invitationUser.id in (select tis.invitationUser.id from TokenUserInvitationToOrganization tis where tis.token = :token)")
    Optional<InvitationOrganizationUser> findByTokenInvitationUser(@Param("token") final String token);
}