package com.vntana.core.model.whitelist.response;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.whitelist.error.WhitelistIpErrorResponseModel;
import com.vntana.core.model.whitelist.response.model.GetWhitelistIpGridResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 6:15 PM
 */
public class GetWhitelistIpsByOrganizationResponse extends AbstractResultResponseModel<GetWhitelistIpGridResponseModel, WhitelistIpErrorResponseModel> {

    public GetWhitelistIpsByOrganizationResponse() {
        super();
    }

    public GetWhitelistIpsByOrganizationResponse(final List<WhitelistIpErrorResponseModel> errors) {
        super(errors);
    }

    public GetWhitelistIpsByOrganizationResponse(final GetWhitelistIpGridResponseModel response) {
        super(response);
    }
}
