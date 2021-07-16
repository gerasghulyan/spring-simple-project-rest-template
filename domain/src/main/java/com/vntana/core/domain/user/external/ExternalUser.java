package com.vntana.core.domain.user.external;

import com.vntana.commons.persistence.domain.AbstractDomainEntity;
import com.vntana.core.domain.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Diana Gevorgyan
 * Date: 7/8/2021
 * Time: 5:19 PM
 */
@Entity
@Table(name = "external_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"external_uuid"}, name = "uk_external_user_uuid")},
        indexes = {@Index(columnList = "external_uuid", name = "uk_external_user_uuid", unique = true)}
)
public class ExternalUser extends AbstractDomainEntity {
    @Column(name = "external_uuid",
            nullable = false,
            updatable = false,
            unique = true
    )
    private String externalUuid;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "external_user_user_id_fk")
    )
    private User targetUser;
    
    public ExternalUser() {
    }

    public ExternalUser(
            final String externalUuid,
            final User targetUser) {
        this.externalUuid = externalUuid;
        this.targetUser = targetUser;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExternalUser)) {
            return false;
        }
        final ExternalUser that = (ExternalUser) o;
        return new EqualsBuilder()
                .append(externalUuid, that.externalUuid)
                .append(targetUser, getIdOrNull(that.targetUser))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(externalUuid)
                .append(targetUser)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("externalUuid", externalUuid)
                .append("user", targetUser)
                .toString();
    }

    public String getExternalUuid() {
        return externalUuid;
    }

    public void setExternalUuid(final String externalUuid) {
        this.externalUuid = externalUuid;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(final User targetUser) {
        this.targetUser = targetUser;
    }
}
