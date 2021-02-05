package com.vntana.core.service.organization.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Diana Gevorgyan
 * Date: 1/25/21
 * Time: 8:22 PM
 */
public class OrganizationPaidOutsideStripeDto implements ServiceDto {

    private final String uuid;
    private final boolean paidOutsideStripe;

    public OrganizationPaidOutsideStripeDto(final String uuid, final boolean paidOutsideStripe) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        this.uuid = uuid;
        this.paidOutsideStripe = paidOutsideStripe;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationPaidOutsideStripeDto)) {
            return false;
        }
        final OrganizationPaidOutsideStripeDto that = (OrganizationPaidOutsideStripeDto) o;
        return new EqualsBuilder()
                .append(paidOutsideStripe, that.paidOutsideStripe)
                .append(uuid, that.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(paidOutsideStripe)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuid", uuid)
                .append("paidOutsideStripe", paidOutsideStripe)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isPaidOutsideStripe() {
        return paidOutsideStripe;
    }
}
