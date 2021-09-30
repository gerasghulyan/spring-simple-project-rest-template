package com.vntana.core.model.catalog.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.catalog.error.FacebookCatalogSettingErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/30/2021
 * Time: 2:21 PM
 */
public class GetByCatalogIdFacebookCatalogSettingRequest extends AbstractRequestModel implements ValidatableRequest<FacebookCatalogSettingErrorResponseModel> {

    @JsonProperty("catalogId")
    private String catalogId;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public GetByCatalogIdFacebookCatalogSettingRequest() {
    }

    public GetByCatalogIdFacebookCatalogSettingRequest(final String catalogId, final String organizationUuid) {
        this.catalogId = catalogId;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<FacebookCatalogSettingErrorResponseModel> validate() {
        final List<FacebookCatalogSettingErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(organizationUuid)) {
            errors.add(FacebookCatalogSettingErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (StringUtils.isBlank(catalogId)) {
            errors.add(FacebookCatalogSettingErrorResponseModel.MISSING_CATALOG_ID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetByCatalogIdFacebookCatalogSettingRequest)) {
            return false;
        }
        final GetByCatalogIdFacebookCatalogSettingRequest that = (GetByCatalogIdFacebookCatalogSettingRequest) o;
        return new EqualsBuilder()
                .append(catalogId, that.catalogId)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(catalogId)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("catalogId", catalogId)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(final String catalogId) {
        this.catalogId = catalogId;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
