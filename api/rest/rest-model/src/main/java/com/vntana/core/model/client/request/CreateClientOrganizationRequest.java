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
 * Date: 10/10/19
 * Time: 10:40 AM
 */
public class CreateClientOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<ClientOrganizationErrorResponseModel> {
    @JsonProperty("name")
    private String name;

    @JsonProperty("slug")
    private String slug;

    public CreateClientOrganizationRequest() {
    }

    public CreateClientOrganizationRequest(final String name, final String slug) {
        this.name = name;
        this.slug = slug;
    }

    @Override
    public List<ClientOrganizationErrorResponseModel> validate() {
        final List<ClientOrganizationErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(name)) {
            errors.add(ClientOrganizationErrorResponseModel.MISSING_NAME);
        }
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
        if (!(o instanceof CreateClientOrganizationRequest)) {
            return false;
        }
        final CreateClientOrganizationRequest that = (CreateClientOrganizationRequest) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(slug, that.slug)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("slug", slug)
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }
}
