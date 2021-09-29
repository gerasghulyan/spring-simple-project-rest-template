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

    @JsonProperty("catalogs")
    private List<FacebookCatalogSetting> catalogs;

    @JsonProperty("systemUserToken")
    private String systemUserToken;

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    public CreateFacebookCatalogSettingRequest() {
    }

    public CreateFacebookCatalogSettingRequest(final List<FacebookCatalogSetting> catalogs) {
        this.catalogs = catalogs;
    }

    public CreateFacebookCatalogSettingRequest(
            final List<FacebookCatalogSetting> catalogs,
            final String systemUserToken,
            final String organizationUuid) {
        this.catalogs = catalogs;
        this.systemUserToken = systemUserToken;
        this.organizationUuid = organizationUuid;
    }

    @Override
    public List<FacebookCatalogSettingErrorResponseModel> validate() {
        final List<FacebookCatalogSettingErrorResponseModel> errors = initializeNew();
        if (catalogs == null) {
            errors.add(FacebookCatalogSettingErrorResponseModel.FACEBOOK_CATALOG_SETTINGS_CANNOT_BE_NULL);
        }
        if (StringUtils.isBlank(systemUserToken)) {
            errors.add(FacebookCatalogSettingErrorResponseModel.MISSING_SYSTEM_USER_TOKEN);
        }
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
        if (!(o instanceof CreateFacebookCatalogSettingRequest)) {
            return false;
        }
        final CreateFacebookCatalogSettingRequest that = (CreateFacebookCatalogSettingRequest) o;
        return new EqualsBuilder()
                .append(catalogs, that.catalogs)
                .append(systemUserToken, that.systemUserToken)
                .append(organizationUuid, that.organizationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(catalogs)
                .append(systemUserToken)
                .append(organizationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("catalogs", catalogs)
                .append("systemUserToken", systemUserToken)
                .append("organizationUuid", organizationUuid)
                .toString();
    }

    public List<FacebookCatalogSetting> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(final List<FacebookCatalogSetting> catalogs) {
        this.catalogs = catalogs;
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
}
