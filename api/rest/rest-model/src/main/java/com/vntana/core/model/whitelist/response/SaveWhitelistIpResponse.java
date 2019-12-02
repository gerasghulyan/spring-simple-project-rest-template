package com.vntana.core.model.whitelist.response;

import com.vntana.commons.api.model.response.impl.EmptyResultResponseModel;
import com.vntana.core.model.whitelist.error.WhitelistIpErrorResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 5:51 PM
 */
public class SaveWhitelistIpResponse extends EmptyResultResponseModel<WhitelistIpErrorResponseModel> {

    public SaveWhitelistIpResponse() {
    }

    public SaveWhitelistIpResponse(final List<WhitelistIpErrorResponseModel> errors) {
        super(errors);
    }
}
