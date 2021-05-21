package com.vntana.core.service.whitelist;

import com.vntana.core.domain.whitelist.WhitelistIp;
import com.vntana.core.domain.whitelist.WhitelistType;
import com.vntana.core.service.whitelist.dto.CreateWhitelistIpDto;
import com.vntana.core.service.whitelist.dto.UpdateWhitelistIpDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:35 PM
 */
public interface WhitelistIpService {

    WhitelistIp create(final CreateWhitelistIpDto createDto);

    Optional<WhitelistIp> findByUuid(final String uuid);

    List<WhitelistIp> getByOrganization(final String organizationUuid);
    
    List<WhitelistIp> getByOrganizationAndType(final String organizationUuid, final WhitelistType type);

    WhitelistIp update(final UpdateWhitelistIpDto updateDto);

    void delete(final List<String> uuids);
}
