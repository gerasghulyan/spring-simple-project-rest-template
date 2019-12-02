package com.vntana.core.rest.facade.whitelist;

import com.vntana.core.model.whitelist.request.SaveWhitelistIpsRequest;
import com.vntana.core.model.whitelist.response.SaveWhitelistIpResponse;
import com.vntana.core.model.whitelist.response.GetWhitelistIpsByOrganizationResponse;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 5:43 PM
 */
public interface WhitelistIpServiceFacade {

    SaveWhitelistIpResponse save(final SaveWhitelistIpsRequest request);

    GetWhitelistIpsByOrganizationResponse getByOrganization(final String organizationUuid);
}
