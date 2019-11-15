package com.vntana.core.model.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
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

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public CheckAvailableClientOrganizationSlugRequest() {
        super();
    }

    public CheckAvailableClientOrganizationSlugRequest(final String slug, final String organizationUuid) {
        this.slug = slug;
        this.organizationUuid = organizationUuid;
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
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(slug)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("slug", slug)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
