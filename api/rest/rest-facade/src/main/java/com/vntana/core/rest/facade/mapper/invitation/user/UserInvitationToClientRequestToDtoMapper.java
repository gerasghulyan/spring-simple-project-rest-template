package com.vntana.core.rest.facade.mapper.invitation.user;

import com.vntana.core.model.token.request.CreateTokenUserInvitationToClientRequest;
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToClientDto;
import com.vntana.core.service.token.invitation.user.dto.InvitationUuidAndTokenDto;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created by Diana Gevorgyan
 * Date: 11/23/20
 * Time: 2:27 PM
 */
@Component
public class UserInvitationToClientRequestToDtoMapper extends CustomConverter<CreateTokenUserInvitationToClientRequest, CreateInvitationUserToClientDto> {
    
    @Override
    public CreateInvitationUserToClientDto convert(
            final CreateTokenUserInvitationToClientRequest request,
            final Type<? extends CreateInvitationUserToClientDto> destinationType,
            final MappingContext mappingContext) {
        return new CreateInvitationUserToClientDto(
                request
                        .getTokens()
                        .stream()
                        .map(it -> new InvitationUuidAndTokenDto(it.getUserInvitationUuid(), it.getToken()))
                        .collect(Collectors.toList())
        );
    }
}
