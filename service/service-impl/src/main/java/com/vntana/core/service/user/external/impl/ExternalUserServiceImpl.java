package com.vntana.core.service.user.external.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.domain.user.external.ExternalUser;
import com.vntana.core.persistence.user.UserRepository;
import com.vntana.core.persistence.user.extrenal.ExternalUserRepository;
import com.vntana.core.service.user.external.ExternalUserService;
import com.vntana.core.service.user.external.dto.GetOrCreateExternalUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 12:19 PM
 */
@Service
public class ExternalUserServiceImpl implements ExternalUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalUserServiceImpl.class);

    private final ExternalUserRepository repository;
    private final UserRepository userRepository;

    public ExternalUserServiceImpl(final ExternalUserRepository repository, final UserRepository userRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public ExternalUser getOrCreate(final GetOrCreateExternalUserDto dto) {
        LOGGER.debug("Getting anonymous user with provided dto - {}", dto);
        Assert.notNull(dto, "The GetOrCreateAnonymousUserDto cannot be null or empty");
        return repository.findByExternalUuid(dto.getExternalUuid())
                .orElseGet(() -> createNewAnonymousUser(dto));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ExternalUser> findByExternalUuidAndSource(final String externalUuid) {
        Assert.hasText(externalUuid, "External uuid cannot be null or empty");
        LOGGER.debug("Searching existing anonymous user with external uuid - {}", externalUuid);
        final Optional<ExternalUser> result = repository.findByExternalUuid(externalUuid);
        LOGGER.debug("Done searching existing anonymous user with external uuid - {}", externalUuid);
        return result;
    }

    private ExternalUser createNewAnonymousUser(final GetOrCreateExternalUserDto dto) {
        LOGGER.debug("Creating new anonymous user for provided dto - {}", dto);
        final User newOrganizationAdmin = createRandomUserWithOrganizationClientAdminRole(dto.getClientOrganization());
        final ExternalUser result = repository.save(new ExternalUser(dto.getExternalUuid(), newOrganizationAdmin));
        LOGGER.debug("Done creating anonymous user with result - {}", result);
        return result;
    }

    private User createRandomUserWithOrganizationClientAdminRole(final ClientOrganization clientOrganization) {
        final User user = userRepository.save(
                new User(
                        randomAlphabetic(5),
                        null,
                        null));
        user.grantClientRole(clientOrganization, UserRole.CLIENT_ORGANIZATION_ADMIN);
        return user;
    }
}
