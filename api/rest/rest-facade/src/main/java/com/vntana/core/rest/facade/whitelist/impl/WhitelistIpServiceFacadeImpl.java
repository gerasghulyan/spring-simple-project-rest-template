package com.vntana.core.rest.facade.whitelist.impl;

import com.vntana.core.domain.whitelist.WhitelistIp;
import com.vntana.core.model.whitelist.error.WhitelistIpErrorResponseModel;
import com.vntana.core.model.whitelist.request.CreateOrUpdateWhitelistIpItemRequestModel;
import com.vntana.core.model.whitelist.request.SaveWhitelistIpsRequest;
import com.vntana.core.model.whitelist.response.SaveWhitelistIpResponse;
import com.vntana.core.model.whitelist.response.GetWhitelistIpsByOrganizationResponse;
import com.vntana.core.model.whitelist.response.model.GetWhitelistIpGridResponseModel;
import com.vntana.core.model.whitelist.response.model.GetWhitelistIpResponseModel;
import com.vntana.core.rest.facade.whitelist.WhitelistIpServiceFacade;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.whitelist.WhitelistIpService;
import com.vntana.core.service.whitelist.dto.CreateWhitelistIpDto;
import io.vavr.collection.Stream;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 5:44 PM
 */
@Component
public class WhitelistIpServiceFacadeImpl implements WhitelistIpServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhitelistIpServiceFacadeImpl.class);

    private final MapperFacade mapperFacade;
    private final OrganizationService organizationService;
    private final WhitelistIpService whitelistIpService;

    public WhitelistIpServiceFacadeImpl(final MapperFacade mapperFacade, final OrganizationService organizationService, final WhitelistIpService whitelistIpService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.mapperFacade = mapperFacade;
        this.organizationService = organizationService;
        this.whitelistIpService = whitelistIpService;
    }

    @Transactional
    @Override
    public SaveWhitelistIpResponse save(final SaveWhitelistIpsRequest request) {
        LOGGER.debug("Processing WhitelistIp resource save method for request - {}", request);
        final List<WhitelistIpErrorResponseModel> possibleErrors = checkCreateOrUpdateForErrors(request);
        if (!possibleErrors.isEmpty()) {
            return new SaveWhitelistIpResponse(possibleErrors);
        }
        final List<String> uuids = whitelistIpService.getByOrganization(request.getOrganizationUuid()).stream()
                .map(WhitelistIp::getUuid)
                .collect(Collectors.toList());
        if (!uuids.isEmpty()) {
            whitelistIpService.delete(uuids);
        }
        Stream.ofAll(request.getWhitelistIps())
                .distinctBy(Comparator.comparing(CreateOrUpdateWhitelistIpItemRequestModel::getIp))
                .forEach(model -> {
                    final CreateWhitelistIpDto createDto = new CreateWhitelistIpDto(model.getLabel(), model.getIp(), request.getOrganizationUuid());
                    whitelistIpService.create(createDto);
                });
        LOGGER.debug("Successfully processed WhitelistIp resource create method for request - {}", request);
        return new SaveWhitelistIpResponse();
    }

    @Transactional
    @Override
    public GetWhitelistIpsByOrganizationResponse getByOrganization(final String organizationUuid) {
        LOGGER.debug("Processing WhitelistIp resource getByOrganization method for organizationUuid - {}", organizationUuid);
        final List<WhitelistIp> whitelistIps = whitelistIpService.getByOrganization(organizationUuid);
        final List<GetWhitelistIpResponseModel> responseModels = mapperFacade.mapAsList(whitelistIps, GetWhitelistIpResponseModel.class);
        LOGGER.debug("Successfully processed WhitelistIp resource getByOrganization method for organizationUuid - {}", organizationUuid);
        return new GetWhitelistIpsByOrganizationResponse(new GetWhitelistIpGridResponseModel(responseModels.size(), responseModels));
    }

    private List<WhitelistIpErrorResponseModel> checkCreateOrUpdateForErrors(final SaveWhitelistIpsRequest request) {
        final boolean anyOrganizationNotFound = organizationService.existsByUuid(request.getOrganizationUuid());
        if (!anyOrganizationNotFound) {
            return Collections.singletonList(WhitelistIpErrorResponseModel.ORGANIZATION_NOT_FOUND);
        }
        return Collections.emptyList();
    }
}
