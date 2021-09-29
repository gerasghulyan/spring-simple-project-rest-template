package com.vntana.core.rest.facade.catalog.impl;

import com.vntana.core.domain.catalog.FacebookCatalogSetting;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel;
import com.vntana.core.model.catalog.request.CreateFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.request.GetByOrganizationFacebookCatalogSettingRequest;
import com.vntana.core.model.catalog.response.CreateFacebookCatalogSettingResultResponse;
import com.vntana.core.model.catalog.response.DeleteFacebookCatalogSettingResultResponse;
import com.vntana.core.model.catalog.response.GetByOrganizationFacebookCatalogSettingResponseModel;
import com.vntana.core.model.catalog.response.GetByOrganizationFacebookCatalogSettingResultResponse;
import com.vntana.core.rest.facade.catalog.FacebookCatalogSettingServiceFacade;
import com.vntana.core.service.catalog.FacebookCatalogSettingService;
import com.vntana.core.service.catalog.dto.CreateFacebookCatalogSettingDto;
import com.vntana.core.service.catalog.dto.GetByOrganizationFacebookCatalogSettingDto;
import com.vntana.core.service.organization.OrganizationService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 8:45 AM
 */
@Component
public class FacebookCatalogSettingServiceFacadeImpl implements FacebookCatalogSettingServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookCatalogSettingServiceFacadeImpl.class);

    private final FacebookCatalogSettingService facebookCatalogSettingService;
    private final OrganizationService organizationService;

    public FacebookCatalogSettingServiceFacadeImpl(
            final FacebookCatalogSettingService facebookCatalogSettingService,
            final OrganizationService organizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.facebookCatalogSettingService = facebookCatalogSettingService;
        this.organizationService = organizationService;
    }

    @Override
    public CreateFacebookCatalogSettingResultResponse create(final CreateFacebookCatalogSettingRequest request) {
        final Optional<Organization> organizationOptional = organizationService.findByUuid(request.getOrganizationUuid());
        if (organizationOptional.isEmpty()) {
            LOGGER.info("Cannot find organization for uuid - {}", request.getOrganizationUuid());
            return new CreateFacebookCatalogSettingResultResponse(
                    HttpStatus.SC_NOT_FOUND,
                    FacebookCatalogSettingErrorResponseModel.ORGANIZATION_NOT_FOUND
            );
        }
        LOGGER.debug("Creating facebook catalog setting for request - {}", request);
        final List<String> catalogUuids = request.getCatalogs().stream().map(
                catalog -> {
                    final FacebookCatalogSetting facebookCatalogSetting = facebookCatalogSettingService.create(
                            new CreateFacebookCatalogSettingDto(request.getSystemUserToken(), catalog.getName(), catalog.getCatalogId(), organizationOptional.get())
                    );
                    return facebookCatalogSetting.getUuid();
                }
        ).collect(Collectors.toList());
        LOGGER.debug("Done creating facebook catalog setting with response - {}", catalogUuids);
        return new CreateFacebookCatalogSettingResultResponse(catalogUuids);
    }

    @Override
    public GetByOrganizationFacebookCatalogSettingResultResponse getByOrganization(final GetByOrganizationFacebookCatalogSettingRequest request) {
        final Optional<Organization> organizationOptional = organizationService.findByUuid(request.getOrganizationUuid());
        if (organizationOptional.isEmpty()) {
            LOGGER.info("Cannot find organization for uuid - {}", request.getOrganizationUuid());
            return new GetByOrganizationFacebookCatalogSettingResultResponse(
                    HttpStatus.SC_NOT_FOUND,
                    FacebookCatalogSettingErrorResponseModel.ORGANIZATION_NOT_FOUND
            );
        }
        LOGGER.debug("Getting facebook catalog settings for organization uuid - {}", request.getOrganizationUuid());
        final Page<FacebookCatalogSetting> foundFacebookCatalogSettings = facebookCatalogSettingService.getByOrganization(
                new GetByOrganizationFacebookCatalogSettingDto(
                        request.getPage(),
                        request.getSize(),
                        organizationOptional.get()));
        LOGGER.debug("Successfully done getting facebook catalog settings for organization uuid - {}, total items - {}", request.getOrganizationUuid(), foundFacebookCatalogSettings.getTotalElements());
        return constructResultResponse(foundFacebookCatalogSettings);
    }

    @Override
    public DeleteFacebookCatalogSettingResultResponse delete(final String uuid) {
        final Optional<FacebookCatalogSetting> catalogSetting = facebookCatalogSettingService.findByUuid(uuid);
        if (catalogSetting.isEmpty()) {
            LOGGER.info("Cannot find facebook catalog setting for uuid - {}", uuid);
            return new DeleteFacebookCatalogSettingResultResponse(
                    HttpStatus.SC_NOT_FOUND,
                    FacebookCatalogSettingErrorResponseModel.FACEBOOK_CATALOG_SETTING_NOT_FOUND
            );
        }
        LOGGER.debug("Deleting facebook catalog setting for uuid - {}", uuid);
        facebookCatalogSettingService.delete(catalogSetting.get());
        LOGGER.debug("Successfully done deleting facebook catalog setting for uuid - {}", uuid);
        return new DeleteFacebookCatalogSettingResultResponse(uuid);
    }

    private GetByOrganizationFacebookCatalogSettingResultResponse constructResultResponse(final Page<FacebookCatalogSetting> foundFacebookCatalogSettings) {
        final List<GetByOrganizationFacebookCatalogSettingResponseModel> responseModels = foundFacebookCatalogSettings.stream()
                .map(catalog -> new GetByOrganizationFacebookCatalogSettingResponseModel(
                        catalog.getUuid(),
                        catalog.getSystemUserToken(),
                        catalog.getName(),
                        catalog.getCatalogId())).collect(Collectors.toList());
        return new GetByOrganizationFacebookCatalogSettingResultResponse(foundFacebookCatalogSettings.getSize(), responseModels);
    }
}
