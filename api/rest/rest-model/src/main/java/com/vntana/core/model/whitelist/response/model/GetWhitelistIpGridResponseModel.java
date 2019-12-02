package com.vntana.core.model.whitelist.response.model;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 6:17 PM
 */
public class GetWhitelistIpGridResponseModel extends AbstractGridResponseModel<GetWhitelistIpResponseModel> {

    private GetWhitelistIpGridResponseModel() {
        super();
    }

    public GetWhitelistIpGridResponseModel(final int totalCount, final List<GetWhitelistIpResponseModel> items) {
        super(totalCount, items);
    }
}
