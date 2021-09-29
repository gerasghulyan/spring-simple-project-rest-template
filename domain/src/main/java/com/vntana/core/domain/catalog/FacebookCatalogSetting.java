package com.vntana.core.domain.catalog;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 2:41 PM
 */
@Entity
@Table(name = "facebook_catalog_setting", uniqueConstraints = {
        @UniqueConstraint(name = "uk_facebook_catalog_setting_uuid", columnNames = {"uuid"})
})
public class FacebookCatalogSetting extends AbstractUuidAwareDomainEntity {

    @Column(name = "system_user_token", updatable = false, nullable = false)
    private String systemUserToken;

    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @Column(name = "catalog_id", updatable = false, nullable = false)
    private String catalogId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_organization_id"), updatable = false)
    private Organization organization;

    public FacebookCatalogSetting() {
    }

    public FacebookCatalogSetting(
            final String systemUserToken,
            final String name,
            final String catalogId,
            final Organization organization) {
        super();
        this.systemUserToken = systemUserToken;
        this.name = name;
        this.catalogId = catalogId;
        this.organization = organization;
    }

    public FacebookCatalogSetting(
            final String uuid,
            final String systemUserToken,
            final String name,
            final String catalogId,
            final Organization organization) {
        super(uuid);
        this.systemUserToken = systemUserToken;
        this.name = name;
        this.catalogId = catalogId;
        this.organization = organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacebookCatalogSetting)) {
            return false;
        }
        final FacebookCatalogSetting that = (FacebookCatalogSetting) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(systemUserToken, that.systemUserToken)
                .append(name, that.name)
                .append(catalogId, that.catalogId)
                .append(organization, that.organization)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(systemUserToken)
                .append(name)
                .append(catalogId)
                .append(organization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("systemUserToken", systemUserToken)
                .append("name", name)
                .append("catalogId", catalogId)
                .append("organization", organization)
                .toString();
    }

    public String getSystemUserToken() {
        return systemUserToken;
    }

    public void setSystemUserToken(final String systemUserToken) {
        this.systemUserToken = systemUserToken;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(final Organization organization) {
        this.organization = organization;
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
