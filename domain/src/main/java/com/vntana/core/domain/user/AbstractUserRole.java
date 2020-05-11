package com.vntana.core.domain.user;

import com.vntana.commons.persistence.domain.AbstractDomainEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/16/19
 * Time: 11:50 AM
 */
@Entity
@Table(name = "user_role")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn(name = "type")
public abstract class AbstractUserRole extends AbstractDomainEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_id"), updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    AbstractUserRole() {
        super();
    }

    public AbstractUserRole(final User user, final UserRole userRole) {
        this.user = user;
        this.userRole = userRole;
    }

    public User getUser() {
        return user;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("user_id", getIdOrNull(user))
                .append("userRole", userRole)
                .toString();
    }
}
