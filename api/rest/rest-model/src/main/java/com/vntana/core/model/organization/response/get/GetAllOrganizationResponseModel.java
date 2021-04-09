package com.vntana.core.model.organization.response.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 3:14 PM
 */
public class GetAllOrganizationResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("name")
    private String name;

    @JsonProperty("slug")
    private String slug;

    public GetAllOrganizationResponseModel() {
        super();
    }

    public GetAllOrganizationResponseModel(final String uuid, final String name, final String slug) {
        super(uuid);
        this.name = name;
        this.slug = slug;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", name)
                .append("slug", slug)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetAllOrganizationResponseModel)) {
            return false;
        }
        final GetAllOrganizationResponseModel that = (GetAllOrganizationResponseModel) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(slug, that.slug)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .toHashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }

}