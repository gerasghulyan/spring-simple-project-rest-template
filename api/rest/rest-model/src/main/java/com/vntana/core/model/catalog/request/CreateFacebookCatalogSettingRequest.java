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
 * Date: 9/27/2021
 * Time: 8:41 AM
 */
public class CreateFacebookCatalogSettingRequest extends AbstractRequestModel implements ValidatableRequest<FacebookCatalogSettingErrorResponseModel> {

    @JsonProperty("systemUserToken")
    private String systemUserToken;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("catalogId")
    private String catalogId;

    public CreateFacebookCatalogSettingRequest() {
    }

    public CreateFacebookCatalogSettingRequest(
            final String systemUserToken,
            final String organizationUuid,
            final String name,
            final String catalogId) {
        this.systemUserToken = systemUserToken;
        this.organizationUuid = organizationUuid;
        this.name = name;
        this.catalogId = catalogId;
    }

    @Override
    public List<FacebookCatalogSettingErrorResponseModel> validate() {
        final List<FacebookCatalogSettingErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(systemUserToken)) {
            errors.add(FacebookCatalogSettingErrorResponseModel.MISSING_SYSTEM_USER_TOKEN);
        }
        if (StringUtils.isBlank(organizationUuid)) {
            errors.add(FacebookCatalogSettingErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        if (StringUtils.isBlank(name)) {
            errors.add(FacebookCatalogSettingErrorResponseModel.MISSING_NAME);
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
        if (!(o instanceof CreateFacebookCatalogSettingRequest)) {
            return false;
        }
        final CreateFacebookCatalogSettingRequest that = (CreateFacebookCatalogSettingRequest) o;
        return new EqualsBuilder()
                .append(systemUserToken, that.systemUserToken)
                .append(organizationUuid, that.organizationUuid)
                .append(name, that.name)
                .append(catalogId, that.catalogId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(systemUserToken)
                .append(organizationUuid)
                .append(name)
                .append(catalogId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("systemUserToken", systemUserToken)
                .append("organizationUuid", organizationUuid)
                .append("name", name)
                .append("catalogId", catalogId)
                .toString();
    }

    public String getSystemUserToken() {
        return systemUserToken;
    }

    public void setSystemUserToken(final String systemUserToken) {
        this.systemUserToken = systemUserToken;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(final String catalogId) {
        this.catalogId = catalogId;
    }
}
