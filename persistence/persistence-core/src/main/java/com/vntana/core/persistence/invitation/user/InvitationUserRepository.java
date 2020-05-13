package com.vntana.core.persistence.invitation.user;

import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:05 PM
 */
@Repository
public interface InvitationUserRepository extends JpaRepository<InvitationUser, Long> {

    Optional<InvitationUser> findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);

    List<InvitationUser> findByInviterUserUuid(final String uuid);

    List<InvitationUser> findByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(final String email, final String organizationUuid, final InvitationStatus status);

    Page<InvitationUser> findAllByStatus(final InvitationStatus status, final Pageable pageable);
}