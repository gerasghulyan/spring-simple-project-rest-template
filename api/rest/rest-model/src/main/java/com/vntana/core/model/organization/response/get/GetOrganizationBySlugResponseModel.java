package com.vntana.core.model.organization.response.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class GetOrganizationBySlugResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("name")
    private String name;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("imageId")
    private String imageId;

    public GetOrganizationBySlugResponseModel() {
        super();
    }

    public GetOrganizationBySlugResponseModel(final String uuid, final String name, final String slug, final String imageId) {
        super(uuid);
        this.name = name;
        this.slug = slug;
        this.imageId = imageId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetOrganizationBySlugResponseModel)) {
            return false;
        }
        final GetOrganizationBySlugResponseModel that = (GetOrganizationBySlugResponseModel) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(slug, that.slug)
                .append(imageId, that.imageId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(imageId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("slug", slug)
                .append("imageId", imageId)
                .toString();
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(final String imageId) {
        this.imageId = imageId;
    }
}
