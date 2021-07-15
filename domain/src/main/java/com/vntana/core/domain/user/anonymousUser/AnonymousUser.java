package com.vntana.core.domain.user.anonymousUser;

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
@Table(name = "anonymous_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"external_uuid"}, name = "uk_anonymous_user_external_uuid")},
        indexes = {@Index(columnList = "external_uuid", name = "uk_anonymous_user_external_uuid", unique = true)}
)
public class AnonymousUser {

    @Id
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
            foreignKey = @ForeignKey(name = "anonymous_user_user_id_fk")
    )
    private User targetUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "anonymous_user_source",
            nullable = false
    )
    private AnonymousUserSource source;

    public AnonymousUser() {
    }

    public AnonymousUser(
            final String externalUuid,
            final User targetUser,
            final AnonymousUserSource source) {
        this.externalUuid = externalUuid;
        this.targetUser = targetUser;
        this.source = source;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnonymousUser)) {
            return false;
        }
        final AnonymousUser that = (AnonymousUser) o;
        return new EqualsBuilder()
                .append(externalUuid, that.externalUuid)
                .append(targetUser, that.targetUser)
                .append(source, that.source)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(externalUuid)
                .append(targetUser)
                .append(source)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("externalUuid", externalUuid)
                .append("user", targetUser)
                .append("anonymousUserIntegration", source)
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

    public AnonymousUserSource getAnonymousUserSource() {
        return source;
    }

    public void setAnonymousUserSource(final AnonymousUserSource anonymousUserSource) {
        this.source = anonymousUserSource;
    }
}
