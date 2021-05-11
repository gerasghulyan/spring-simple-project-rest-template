package com.vntana.core.model.storage.client.response.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.vntana.commons.utils.DataSanitizerUtils.mask;

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 17:24
 */
public class CreateStorageClientOrganizationKeyResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("name")
    private String name;

    @JsonProperty("ring")
    private String ring;

    public CreateStorageClientOrganizationKeyResponseModel() {
        super();
    }

    public CreateStorageClientOrganizationKeyResponseModel(final String uuid, final String name, final String ring) {
        super(uuid);
        this.name = name;
        this.ring = ring;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateStorageClientOrganizationKeyResponseModel)) {
            return false;
        }
        final CreateStorageClientOrganizationKeyResponseModel that = (CreateStorageClientOrganizationKeyResponseModel) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(ring, that.ring)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(ring)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", mask(name))
                .append("ring", mask(ring))
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getRing() {
        return ring;
    }

    public void setRing(final String ring) {
        this.ring = ring;
    }
}
