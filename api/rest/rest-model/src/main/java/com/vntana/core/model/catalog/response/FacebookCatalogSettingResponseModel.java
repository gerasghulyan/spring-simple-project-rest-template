package com.vntana.core.model.catalog.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 8:41 AM
 */
public class FacebookCatalogSettingResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("catalogId")
    private String catalogId;

    @JsonProperty("systemUserToken")
    private String systemUserToken;

    @JsonProperty("name")
    private String name;

    public FacebookCatalogSettingResponseModel() {
    }

    public FacebookCatalogSettingResponseModel(final String uuid) {
        super(uuid);
    }

    public FacebookCatalogSettingResponseModel(final String uuid, final String catalogId, final String systemUserToken, final String name) {
        super(uuid);
        this.catalogId = catalogId;
        this.systemUserToken = systemUserToken;
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacebookCatalogSettingResponseModel)) {
            return false;
        }
        final FacebookCatalogSettingResponseModel that = (FacebookCatalogSettingResponseModel) o;
        return new EqualsBuilder()
                .append(catalogId, that.catalogId)
                .append(systemUserToken, that.systemUserToken)
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(catalogId)
                .append(systemUserToken)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("catalogId", catalogId)
                .append("systemUserToken", systemUserToken)
                .append("name", name)
                .toString();
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(final String catalogId) {
        this.catalogId = catalogId;
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
}
