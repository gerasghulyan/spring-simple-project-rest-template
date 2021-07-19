package com.vntana.core.service.token.personalaccess;

import com.vntana.core.domain.token.TokenPersonalAccess;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.token.personalaccess.TokenPersonalAccessRepository;
import com.vntana.core.service.token.exception.TokenNotFoundException;
import com.vntana.core.service.token.personalaccess.dto.CreatePersonalAccessTokenDto;
import com.vntana.core.service.token.personalaccess.dto.RegeneratePersonalAccessTokenDto;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Diana Gevorgyan
 * Date: 3/24/21
 * Time: 6:30 PM
 */
@Service
public class PersonalAccessTokenServiceImpl implements PersonalAccessTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalAccessTokenServiceImpl.class);

    private final TokenPersonalAccessRepository personalAccessRepository;
    private final UserService userService;

    public PersonalAccessTokenServiceImpl(final TokenPersonalAccessRepository personalAccessRepository, final UserService userService) {
        this.personalAccessRepository = personalAccessRepository;
        this.userService = userService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Transactional
    @Override
    public TokenPersonalAccess create(final CreatePersonalAccessTokenDto dto) {
        LOGGER.debug("Creating personal access token for dto - {}", dto);
        notNull(dto, "The CreatePersonalAccessTokenDto should not be null");
        final User user = userService.getByUuid(dto.getUserUuid());
        personalAccessRepository.findAllByUserAndExpirationIsNull(user).forEach(token -> {
            expire(token.getUuid());
        });
        final TokenPersonalAccess tokenPersonalAccess = new TokenPersonalAccess(dto.getToken(), user);
        final TokenPersonalAccess savedToken = personalAccessRepository.save(tokenPersonalAccess);
        LOGGER.debug("Done creating personal access token for dto - {}", dto);
        return savedToken;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TokenPersonalAccess> findByToken(final String token) {
        LOGGER.debug("Searching personal access token for token - {}", token);
        notNull(token, "Token should not be null");
        final Optional<TokenPersonalAccess> foundToken = personalAccessRepository.findByTokenAndExpirationIsNull(token);
        LOGGER.debug("Done searching personal access token for token - {} with response - {}", token, foundToken);
        return foundToken;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TokenPersonalAccess> findByUser(final String userUuid) {
        LOGGER.debug("Searching personal access token for user uuid - {}", userUuid);
        notNull(userUuid, "User uuid should not be null");
        final User user = userService.getByUuid(userUuid);
        final Optional<TokenPersonalAccess> foundPersonalAccessToken = personalAccessRepository.findTokenPersonalAccessByUserAndExpirationIsNull(user);
        LOGGER.debug("Done searching personal access token for user uuid - {} with response - {}", userUuid, foundPersonalAccessToken);
        return foundPersonalAccessToken;
    }

    @Transactional
    @Override
    public TokenPersonalAccess expire(final String tokenUuid) {
        LOGGER.debug("Expiring personal access token with uuid - {}", tokenUuid);
        notNull(tokenUuid, "Token uuid should not be null");
        final TokenPersonalAccess token = personalAccessRepository.findByUuid(tokenUuid).orElseThrow(() -> new TokenNotFoundException(String.format("Cannot find TokenPersonalAccess for tokenUuid - %s", tokenUuid)));
        token.expire();
        final TokenPersonalAccess updatedPAT = personalAccessRepository.save(token);
        LOGGER.debug("Done expiring personal access token with uuid - {}", tokenUuid);
        return updatedPAT;
    }

    @Transactional
    @Override
    public TokenPersonalAccess regenerateTokenForUser(final RegeneratePersonalAccessTokenDto dto) {
        LOGGER.debug("Regenerating personal access token for dto - {}", dto);
        notNull(dto, "The RegeneratePersonalAccessTokenDto should not be null");
        final Optional<TokenPersonalAccess> foundValidToken = findByUser(dto.getUserUuid());
        foundValidToken.ifPresent(tokenPersonalAccess -> expire(tokenPersonalAccess.getUuid()));
        final TokenPersonalAccess newPAT = create(new CreatePersonalAccessTokenDto(dto.getUserUuid(), dto.getToken()));
        LOGGER.debug("Done regenerating personal access token for dto - {} with response - {}", dto, newPAT);
        return newPAT;
    }
}
