package com.vntana.core.model.indexation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 1/15/20
 * Time: 12:40 PM
 */
public class OrganizationIndexationByUuidResponseModel implements ResponseModel {

    @JsonProperty("tookMillis")
    private Long tookMillis;

    public OrganizationIndexationByUuidResponseModel() {
    }

    public OrganizationIndexationByUuidResponseModel(final Long tookMillis) {
        this.tookMillis = tookMillis;
    }

    public Long getTookMillis() {
        return tookMillis;
    }

    public void setTookMillis(final Long tookMillis) {
        this.tookMillis = tookMillis;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrganizationIndexationByUuidResponseModel that = (OrganizationIndexationByUuidResponseModel) o;
        return new EqualsBuilder()
                .append(tookMillis, that.tookMillis)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(tookMillis)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tookSeconds", tookMillis)
                .toString();
    }
}