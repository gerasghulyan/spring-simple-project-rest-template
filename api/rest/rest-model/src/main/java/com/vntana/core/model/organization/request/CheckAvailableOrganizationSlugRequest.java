package com.vntana.core.model.organization.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:53 PM
 */
public class CheckAvailableOrganizationSlugRequest extends AbstractRequestModel implements ValidatableRequest<OrganizationErrorResponseModel> {

    @JsonProperty("slug")
    private String slug;

    public CheckAvailableOrganizationSlugRequest() {
    }

    public CheckAvailableOrganizationSlugRequest(final String slug) {
        this.slug = slug;
    }

    @Override
    public List<OrganizationErrorResponseModel> validate() {
        final List<OrganizationErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(slug)) {
            errors.add(OrganizationErrorResponseModel.MISSING_SLUG);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckAvailableOrganizationSlugRequest)) {
            return false;
        }
        final CheckAvailableOrganizationSlugRequest that = (CheckAvailableOrganizationSlugRequest) o;
        return new EqualsBuilder()
                .append(slug, that.slug)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(slug)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("slug", slug)
                .toString();
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }
}
