package com.vntana.core.model.catalog.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 9:07 AM
 */
public class GetByOrganizationFacebookCatalogSettingResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("systemUserToken")
    private String systemUserToken;

    @JsonProperty("catalogName")
    private String name;

    @JsonProperty("catalogId")
    private String catalogId;

    public GetByOrganizationFacebookCatalogSettingResponseModel() {
    }

    public GetByOrganizationFacebookCatalogSettingResponseModel(
            final String uuid,
            final String systemUserToken,
            final String name,
            final String catalogId) {
        super(uuid);
        this.systemUserToken = systemUserToken;
        this.name = name;
        this.catalogId = catalogId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetByOrganizationFacebookCatalogSettingResponseModel)) {
            return false;
        }
        final GetByOrganizationFacebookCatalogSettingResponseModel that = (GetByOrganizationFacebookCatalogSettingResponseModel) o;
        return new EqualsBuilder()
                .append(systemUserToken, that.systemUserToken)
                .append(name, that.name)
                .append(catalogId, that.catalogId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(systemUserToken)
                .append(name)
                .append(catalogId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("systemUserToken", systemUserToken)
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
