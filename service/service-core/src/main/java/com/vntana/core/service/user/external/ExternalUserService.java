package com.vntana.core.service.user.external;

import com.vntana.core.domain.user.external.ExternalUser;
import com.vntana.core.service.user.external.dto.GetOrCreateExternalUserDto;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan
 * Date: 7/9/2021
 * Time: 2:36 PM
 */
public interface ExternalUserService {

    ExternalUser getOrCreate(GetOrCreateExternalUserDto dto);
    
    Optional<ExternalUser> findByExternalUuidAndSource(final String externalUuid);
}
