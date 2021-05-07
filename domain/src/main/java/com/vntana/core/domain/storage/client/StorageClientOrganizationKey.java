package com.vntana.core.domain.storage.client;

import com.vntana.commons.persistence.domain.AbstractDomainEntity;
import com.vntana.core.domain.client.ClientOrganization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Geras Ghulyan
 * Date: 30-Apr-21
 * Time: 14:56
 */
@Entity
@Table(name = "storage_client_organization_key")
public class StorageClientOrganizationKey extends AbstractDomainEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_storage_client_organization_key_client_organization_id"), updatable = false)
    private ClientOrganization clientOrganization;

    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @Column(name = "type", nullable = false, updatable = false)
    private StorageClientOrganizationKeyType type;

    public StorageClientOrganizationKey() {
        super();
    }

    public StorageClientOrganizationKey(final ClientOrganization clientOrganization, final String name, final StorageClientOrganizationKeyType type) {
        super();
        this.clientOrganization = clientOrganization;
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageClientOrganizationKey)) {
            return false;
        }
        final StorageClientOrganizationKey that = (StorageClientOrganizationKey) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getIdOrNull(clientOrganization), getIdOrNull(that.clientOrganization))
                .append(name, that.name)
                .append(type, that.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(getIdOrNull(clientOrganization))
                .append(name)
                .append(type)
                .toHashCode();
    }

    public ClientOrganization getClientOrganization() {
        return clientOrganization;
    }

    public void setClientOrganization(final ClientOrganization clientOrganization) {
        this.clientOrganization = clientOrganization;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("clientOrganization", getIdOrNull(clientOrganization))
                .append("name", name)
                .append("type", type)
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public StorageClientOrganizationKeyType getType() {
        return type;
    }

    public void setType(final StorageClientOrganizationKeyType type) {
        this.type = type;
    }
}
