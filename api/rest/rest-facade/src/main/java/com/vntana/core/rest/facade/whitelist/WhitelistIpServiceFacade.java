package com.vntana.core.rest.facade.whitelist;

import com.vntana.core.model.whitelist.request.CreateOrUpdateWhitelistIpsRequest;
import com.vntana.core.model.whitelist.response.CreateOrUpdateWhitelistIpResponse;
import com.vntana.core.model.whitelist.response.GetWhitelistIpsByOrganizationResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 5:43 PM
 */
public interface WhitelistIpServiceFacade {

    CreateOrUpdateWhitelistIpResponse createOrUpdate(final CreateOrUpdateWhitelistIpsRequest request);

    GetWhitelistIpsByOrganizationResponse getByOrganization(final String organizationUuid);
}
