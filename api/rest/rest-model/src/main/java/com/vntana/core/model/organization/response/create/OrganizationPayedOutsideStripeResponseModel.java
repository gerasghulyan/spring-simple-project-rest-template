package com.vntana.core.model.organization.response.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 1/25/21
 * Time: 9:56 AM
 */
public class OrganizationPayedOutsideStripeResponseModel extends AbstractUuidAwareResponseModel {

    private final boolean paidOutsideStripe;

    @JsonCreator
    public OrganizationPayedOutsideStripeResponseModel(
            @JsonProperty("uuid") final String uuid,
            @JsonProperty("paidOutsideStripe") final boolean paidOutsideStripe) {
        super(uuid);
        this.paidOutsideStripe = paidOutsideStripe;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationPayedOutsideStripeResponseModel)) {
            return false;
        }
        final OrganizationPayedOutsideStripeResponseModel that = (OrganizationPayedOutsideStripeResponseModel) o;
        return new EqualsBuilder()
                .append(paidOutsideStripe, that.paidOutsideStripe)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(paidOutsideStripe)
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
