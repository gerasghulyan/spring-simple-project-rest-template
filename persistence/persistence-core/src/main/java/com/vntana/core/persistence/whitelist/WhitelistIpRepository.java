package com.vntana.core.persistence.whitelist;

import com.vntana.core.domain.whitelist.WhitelistIp;
import com.vntana.core.domain.whitelist.WhitelistType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:42 PM
 */
@Repository
public interface WhitelistIpRepository extends JpaRepository<WhitelistIp, Long> {

    Optional<WhitelistIp> findByUuid(final String uuid);

    List<WhitelistIp> findByOrganization_Uuid(final String organizationUuid);
    
    List<WhitelistIp> findByOrganization_UuidAndType(final String organizationUuid, final WhitelistType type);

    @Modifying
    @Query("delete from WhitelistIp wip where wip.uuid in ?1")
    void deleteByUuid(final List<String> uuids);
}
