package com.vntana.core.service.user.anonymous;

import com.vntana.core.domain.user.anonymousUser.AnonymousUser;
import com.vntana.core.domain.user.anonymousUser.AnonymousUserSource;
import com.vntana.core.service.user.anonymous.dto.GetOrCreateAnonymousUserDto;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan
 * Date: 7/9/2021
 * Time: 2:36 PM
 */
public interface AnonymousUserService {

    AnonymousUser getOrCreate(GetOrCreateAnonymousUserDto dto);
    
    Optional<AnonymousUser> findByExternalUuidAndSource(final String externalUuid, final AnonymousUserSource source);
}
