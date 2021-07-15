package com.vntana.core.service.user.anonymous.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.anonymousUser.AnonymousUser;
import com.vntana.core.domain.user.anonymousUser.AnonymousUserSource;
import com.vntana.core.persistence.user.UserRepository;
import com.vntana.core.persistence.user.anonymous.AnonymousUserRepository;
import com.vntana.core.service.user.anonymous.AnonymousUserService;
import com.vntana.core.service.user.anonymous.dto.GetOrCreateAnonymousUserDto;
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
public class AnonymousUserServiceImpl implements AnonymousUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnonymousUserServiceImpl.class);

    private final AnonymousUserRepository repository;
    private final UserRepository userRepository;

    public AnonymousUserServiceImpl(final AnonymousUserRepository repository, final UserRepository userRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public AnonymousUser getOrCreate(final GetOrCreateAnonymousUserDto dto) {
        LOGGER.debug("Getting anonymous user with provided dto - {}", dto);
        Assert.notNull(dto, "The GetOrCreateAnonymousUserDto cannot be null or empty");
        return repository.findByExternalUuidAndSource(dto.getExternalUuid(), dto.getSource())
                .orElseGet(() -> createNewAnonymousUser(dto));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AnonymousUser> findByExternalUuidAndSource(final String externalUuid, final AnonymousUserSource source) {
        Assert.hasText(externalUuid, "External uuid cannot be null or empty");
        Assert.notNull(source, "AnonymousUserSource cannot be null");
        LOGGER.debug("Searching existing anonymous user with external uuid - {} and anonymous user source - {}", externalUuid, source);
        final Optional<AnonymousUser> result = repository.findByExternalUuidAndSource(externalUuid, source);
        LOGGER.debug("Done searching existing anonymous user with external uuid - {} and anonymous user source - {}", externalUuid, source);
        return result;
    }

    private AnonymousUser createNewAnonymousUser(final GetOrCreateAnonymousUserDto dto) {
        LOGGER.debug("Creating new anonymous user for provided dto - {}", dto);
        final User newOrganizationAdmin = createRandomUserWithOrganizationAdminRole(dto.getOrganization());
        final AnonymousUser result = repository.save(new AnonymousUser(dto.getExternalUuid(), newOrganizationAdmin, dto.getSource()));
        LOGGER.debug("Done creating anonymous user with result - {}", result);
        return result;
    }

    private User createRandomUserWithOrganizationAdminRole(final Organization organization) {
        final User user = userRepository.save(
                new User(
                        randomAlphabetic(5),
                        randomAlphabetic(5),
                        randomAlphabetic(5)));
        user.grantOrganizationAdminRole(organization);
        return user;
    }
}
