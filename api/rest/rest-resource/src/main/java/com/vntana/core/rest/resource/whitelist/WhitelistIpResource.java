package com.vntana.core.rest.resource.whitelist;

import com.vntana.core.model.whitelist.request.CreateOrUpdateWhitelistIpsRequest;
import com.vntana.core.model.whitelist.response.CreateOrUpdateWhitelistIpResponse;
import com.vntana.core.model.whitelist.response.GetWhitelistIpsByOrganizationResponse;
import com.vntana.core.rest.facade.whitelist.WhitelistIpServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 12:16 PM
 */
@RestController
@RequestMapping(value = "/whitelist-ips",
        produces = "application/json"
)
public class WhitelistIpResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhitelistIpResource.class);

    private final WhitelistIpServiceFacade whitelistIpServiceFacade;

    public WhitelistIpResource(final WhitelistIpServiceFacade whitelistIpServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.whitelistIpServiceFacade = whitelistIpServiceFacade;
    }

    @PostMapping(path = "/create-or-update")
    public CreateOrUpdateWhitelistIpResponse createOrUpdate(@RequestBody final CreateOrUpdateWhitelistIpsRequest request) {
        LOGGER.debug("Processing whitelist ip resource createOrUpdate methods for request - {}", request);
        final CreateOrUpdateWhitelistIpResponse response = whitelistIpServiceFacade.createOrUpdate(request);
        LOGGER.debug("Successfully processed whitelist ip resource createOrUpdate methods for request - {}", request);
        return response;
    }

    @GetMapping(path = "/organizations/{uuid}")
    public GetWhitelistIpsByOrganizationResponse getByOrganization(@PathVariable("uuid") final String organizationUuid) {
        LOGGER.debug("Processing whitelist ip resource getByOrganization methods for organization uuid - {}", organizationUuid);
        final GetWhitelistIpsByOrganizationResponse response = whitelistIpServiceFacade.getByOrganization(organizationUuid);
        LOGGER.debug("Successfully processed whitelist ip resource getByOrganization methods for organization uuid - {}", organizationUuid);
        return response;
    }
}
