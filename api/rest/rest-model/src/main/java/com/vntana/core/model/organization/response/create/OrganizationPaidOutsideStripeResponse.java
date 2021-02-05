package com.vntana.core.model.organization.response.create;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;

/**
 * Created by Diana Gevorgyan
 * Date: 1/25/21
 * Time: 9:54 AM
 */
public class OrganizationPaidOutsideStripeResponse extends AbstractResultResponseModel<OrganizationPayedOutsideStripeResponseModel, OrganizationErrorResponseModel> {
    
    public OrganizationPaidOutsideStripeResponse() {
        super();
    }

    public OrganizationPaidOutsideStripeResponse(final String uuid, final boolean isPayedOutsideStripe) {
        super(new OrganizationPayedOutsideStripeResponseModel(uuid, isPayedOutsideStripe));
    }

    public OrganizationPaidOutsideStripeResponse(final int httpStatusCode, final OrganizationErrorResponseModel error) {
        super(httpStatusCode, error);
    }
}
