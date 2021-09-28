package com.vntana.core.model.catalog.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractPaginationAwareRequestModel;
import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 8:45 AM
 */
public class GetByOrganizationFacebookCatalogSettingRequest extends AbstractPaginationAwareRequestModel implements ValidatableRequest<FacebookCatalogSettingErrorResponseModel> {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public GetByOrganizationFacebookCatalogSettingRequest() {
    }

    public GetByOrganizationFacebookCatalogSettingRequest(
            final Integer page,
            final Integer size,
            final String organizationUuid) {
        super(page, size);
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<FacebookCatalogSettingErrorResponseModel> validate() {
        final List<FacebookCatalogSettingErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(organizationUuid)) {
            errors.add(FacebookCatalogSettingErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetByOrganizationFacebookCatalogSettingRequest)) {
            return false;
        }
        final GetByOrganizationFacebookCatalogSettingRequest that = (GetByOrganizationFacebookCatalogSettingRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
