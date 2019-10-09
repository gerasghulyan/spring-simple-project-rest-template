package com.vntana.core.rest.facade.user.impl;


import com.vntana.core.domain.user.User;
import com.vntana.core.model.user.request.UserCreateRequest;
import com.vntana.core.model.user.response.UserCreateResponseModel;
import com.vntana.core.model.user.response.UserCreateResultResponse;
import com.vntana.core.rest.facade.user.UserFacade;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.UserCreateDto;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:35 PM
 */
@Component
public class UserFacadeImpl implements UserFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacadeImpl.class);

    private final UserService userService;
    private final MapperFacade mapperFacade;

    public UserFacadeImpl(final UserService userService, final MapperFacade mapperFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public UserCreateResultResponse createUser(final UserCreateRequest request) {
        LOGGER.debug("Processing Facade createUser method for request - {}", request);
        Assert.notNull(request, "The USerCreateRequest should not be null");
        final UserCreateDto userCreateDto = mapperFacade.map(request, UserCreateDto.class);
        final User user = userService.createUser(userCreateDto);
        LOGGER.debug("Successfully processed Facade createUser method for request - {}", request);
        return new UserCreateResultResponse(new UserCreateResponseModel(user.getUuid()));
    }
}
