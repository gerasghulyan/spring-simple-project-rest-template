package com.vntana.core.model.organization.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.core.api.models.response.single.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class CheckAvailableOrganizationSlugResponseModel implements ResponseModel {

    @JsonProperty("available")
    private boolean available;

    @JsonProperty("suggested")
    private String suggested;

    public CheckAvailableOrganizationSlugResponseModel() {
    }

    CheckAvailableOrganizationSlugResponseModel(final boolean available, final String suggested) {
        this.available = available;
        this.suggested = suggested;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckAvailableOrganizationSlugResponseModel)) {
            return false;
        }
        final CheckAvailableOrganizationSlugResponseModel that = (CheckAvailableOrganizationSlugResponseModel) o;
        return new EqualsBuilder()
                .append(available, that.available)
                .append(suggested, that.suggested)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(available)
                .append(suggested)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("available", available)
                .append("suggested", suggested)
                .toString();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(final boolean available) {
        this.available = available;
    }

    public String getSuggested() {
        return suggested;
    }

    public void setSuggested(final String suggested) {
        this.suggested = suggested;
    }
}
