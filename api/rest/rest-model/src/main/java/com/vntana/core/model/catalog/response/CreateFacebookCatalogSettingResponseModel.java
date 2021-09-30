package com.vntana.core.model.catalog.response;

import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 8:41 AM
 */
public class CreateFacebookCatalogSettingResponseModel implements ResponseModel {

    private List<String> uuids;

    public CreateFacebookCatalogSettingResponseModel() {
    }

    public CreateFacebookCatalogSettingResponseModel(final List<String> uuids) {
        this.uuids = uuids;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateFacebookCatalogSettingResponseModel)) {
            return false;
        }
        final CreateFacebookCatalogSettingResponseModel that = (CreateFacebookCatalogSettingResponseModel) o;
        return new EqualsBuilder()
                .append(uuids, that.uuids)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuids)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuids", uuids)
                .toString();
    }

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(final List<String> uuids) {
        this.uuids = uuids;
    }
}
