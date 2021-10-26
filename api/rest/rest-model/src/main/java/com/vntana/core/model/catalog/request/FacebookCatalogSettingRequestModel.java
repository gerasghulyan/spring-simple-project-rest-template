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
 * Date: 9/29/2021
 * Time: 5:33 PM
 */
public class FacebookCatalogSettingRequestModel extends AbstractRequestModel implements ValidatableRequest<FacebookCatalogSettingErrorResponseModel> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("catalogId")
    private String catalogId;

    public FacebookCatalogSettingRequestModel() {
    }

    public FacebookCatalogSettingRequestModel(
            final String name,
            final String catalogId) {
        this.name = name;
        this.catalogId = catalogId;
    }

    @Override
    public List<FacebookCatalogSettingErrorResponseModel> validate() {
        final List<FacebookCatalogSettingErrorResponseModel> errors = initializeNew();
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
        if (!(o instanceof FacebookCatalogSettingRequestModel)) {
            return false;
        }
        final FacebookCatalogSettingRequestModel that = (FacebookCatalogSettingRequestModel) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(catalogId, that.catalogId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(catalogId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", name)
                .append("catalogId", catalogId)
                .toString();
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

