package com.vntana.core.domain.whitelist;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 12:51 PM
 */
@Entity
@Table(name = "settings_whitelist_ip")
public class WhitelistIp extends AbstractUuidAwareDomainEntity {

    @Column(name = "label")
    private String label;

    @Column(name = "ip")
    private String ip;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WhitelistType type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_settings_whitelist_ip_organization_id")
    )
    private Organization organization;

    WhitelistIp() {
        super();
    }

    public WhitelistIp(final String label, final String ip, final Organization organization, final WhitelistType type) {
        super();
        this.label = label;
        this.ip = ip;
        this.organization = organization;
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhitelistIp)) {
            return false;
        }
        final WhitelistIp that = (WhitelistIp) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(label, that.label)
                .append(ip, that.ip)
                .append(getIdOrNull(organization), getIdOrNull(that.organization))
                .append(type, that.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(label)
                .append(ip)
                .append(getIdOrNull(organization))
                .append(type)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("label", label)
                .append("ip", ip)
                .append("organizationId", getIdOrNull(organization))
                .append("type", type)
                .toString();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(final Organization organization) {
        this.organization = organization;
    }

    public WhitelistType getType() {
        return type;
    }

    public void setType(final WhitelistType type) {
        this.type = type;
    }
}
