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
        uniqueConstraints = {@UniqueConstraint(columnNames = {"uuid"}, name = "uk_external_user_uuid")},
        indexes = {@Index(columnList = "uuid", name = "uk_external_user_uuid", unique = true)}
)
public class ExternalUser extends AbstractDomainEntity {
    @Column(name = "uuid",
            nullable = false,
            updatable = false,
            unique = true
    )
    private String uuid;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "external_user_user_id_fk")
    )
    private User targetUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "external_user_source",
            nullable = false
    )
    private ExternalUserSource source;

    public ExternalUser() {
    }

    public ExternalUser(
            final String uuid,
            final User targetUser,
            final ExternalUserSource source) {
        this.uuid = uuid;
        this.targetUser = targetUser;
        this.source = source;
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
                .append(uuid, that.uuid)
                .append(targetUser, that.targetUser)
                .append(source, that.source)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(targetUser)
                .append(source)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuid", uuid)
                .append("user", targetUser)
                .append("source", source)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(final User targetUser) {
        this.targetUser = targetUser;
    }

    public ExternalUserSource getAnonymousUserSource() {
        return source;
    }

    public void setAnonymousUserSource(final ExternalUserSource anonymousUserSource) {
        this.source = anonymousUserSource;
    }
}
