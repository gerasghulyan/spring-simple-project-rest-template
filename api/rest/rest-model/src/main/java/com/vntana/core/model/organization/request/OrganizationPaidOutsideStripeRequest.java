package com.vntana.core.model.organization.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractUuidAwareRequestModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 1/25/21
 * Time: 9:42 AM
 */
public class OrganizationPaidOutsideStripeRequest extends AbstractUuidAwareRequestModel implements ValidatableRequest<OrganizationErrorResponseModel> {

    @JsonProperty("paidOutsideStripe")
    private boolean paidOutsideStripe;

    public OrganizationPaidOutsideStripeRequest() {
        super();
    }

    public OrganizationPaidOutsideStripeRequest(final String uuid, final boolean paidOutsideStripe) {
        super(uuid);
        this.paidOutsideStripe = paidOutsideStripe;
    }

    @Override
    public List<OrganizationErrorResponseModel> validate() {
        final List<OrganizationErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(super.getUuid())) {
            errors.add(OrganizationErrorResponseModel.MISSING_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationPaidOutsideStripeRequest)) {
            return false;
        }
        final OrganizationPaidOutsideStripeRequest that = (OrganizationPaidOutsideStripeRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(isPaidOutsideStripe(), that.isPaidOutsideStripe())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(isPaidOutsideStripe())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("paidOutsideStripe", paidOutsideStripe)
                .toString();
    }

    public boolean isPaidOutsideStripe() {
        return paidOutsideStripe;
    }
}
