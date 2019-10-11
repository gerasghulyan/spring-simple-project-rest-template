package com.vntana.core.model.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.api.models.request.ValidatableRequest;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import com.vntana.core.model.commons.request.AbstractRequestModel;
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
public class CheckAvailableClientOrganizationSlugRequest extends AbstractRequestModel implements ValidatableRequest<ClientOrganizationErrorResponseModel> {

    @JsonProperty("slug")
    private String slug;

    public CheckAvailableClientOrganizationSlugRequest() {
    }

    public CheckAvailableClientOrganizationSlugRequest(final String slug) {
        this.slug = slug;
    }

    @Override
    public List<ClientOrganizationErrorResponseModel> validate() {
        final List<ClientOrganizationErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(slug)) {
            errors.add(ClientOrganizationErrorResponseModel.MISSING_SLUG);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckAvailableClientOrganizationSlugRequest)) {
            return false;
        }
        final CheckAvailableClientOrganizationSlugRequest that = (CheckAvailableClientOrganizationSlugRequest) o;
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
