package com.vntana.core.model.client.response.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * Created by Geras Ghulyan
 * Date: 10/9/19
 * Time: 2:55 PM
 */
public class GetClientOrganizationResponseModel implements ResponseModel {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("organizationSlug")
    private String organizationSlug;

    @JsonProperty("clientUuid")
    private String clientUuid;

    @JsonProperty("clientSlug")
    private String clientSlug;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("imageId")
    private String imageId;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("productsCount")
    private Integer productsCount;

    @JsonProperty("locationsCount")
    private Integer locationsCount;

    @JsonProperty("tagsCount")
    private Integer tagsCount;

    public GetClientOrganizationResponseModel() {
    }

    public GetClientOrganizationResponseModel(
            final String organizationUuid,
            final String organizationSlug,
            final String clientUuid,
            final String clientSlug,
            final String clientName,
            final String imageId,
            final LocalDateTime created) {
        this.organizationUuid = organizationUuid;
        this.organizationSlug = organizationSlug;
        this.clientUuid = clientUuid;
        this.clientSlug = clientSlug;
        this.clientName = clientName;
        this.imageId = imageId;
        this.created = created;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetClientOrganizationResponseModel)) {
            return false;
        }
        final GetClientOrganizationResponseModel that = (GetClientOrganizationResponseModel) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(organizationSlug, that.organizationSlug)
                .append(clientUuid, that.clientUuid)
                .append(clientSlug, that.clientSlug)
                .append(clientName, that.clientName)
                .append(imageId, that.imageId)
                .append(created, that.created)
                .append(productsCount, that.productsCount)
                .append(locationsCount, that.locationsCount)
                .append(tagsCount, that.tagsCount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(organizationSlug)
                .append(clientUuid)
                .append(clientSlug)
                .append(clientName)
                .append(imageId)
                .append(created)
                .append(productsCount)
                .append(locationsCount)
                .append(tagsCount)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("organizationSlug", organizationSlug)
                .append("clientUuid", clientUuid)
                .append("clientSlug", clientSlug)
                .append("clientName", clientName)
                .append("imageId", imageId)
                .append("created", created)
                .append("productsCount", productsCount)
                .append("locationsCount", locationsCount)
                .append("tagsCount", tagsCount)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getOrganizationSlug() {
        return organizationSlug;
    }

    public void setOrganizationSlug(final String organizationSlug) {
        this.organizationSlug = organizationSlug;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(final String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public String getClientSlug() {
        return clientSlug;
    }

    public void setClientSlug(final String clientSlug) {
        this.clientSlug = clientSlug;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(final String imageId) {
        this.imageId = imageId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(final Integer productsCount) {
        this.productsCount = productsCount;
    }

    public Integer getLocationsCount() {
        return locationsCount;
    }

    public void setLocationsCount(final Integer locationsCount) {
        this.locationsCount = locationsCount;
    }

    public Integer getTagsCount() {
        return tagsCount;
    }

    public void setTagsCount(final Integer tagsCount) {
        this.tagsCount = tagsCount;
    }
}
