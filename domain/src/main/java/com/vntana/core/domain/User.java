package com.vntana.core.domain;

import com.vntana.core.domain.commons.AbstractUuidAwareDomainEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 5:37 PM
 */
@Entity
@Table(name = "user_")
public class User extends AbstractUuidAwareDomainEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    public User() {
        super();
    }

    public User(final String firstName, final String secondName) {
        super();
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(final String secondName) {
        this.secondName = secondName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User user = (User) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(firstName, user.firstName)
                .append(secondName, user.secondName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(firstName)
                .append(secondName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("secondName", secondName)
                .toString();
    }
}
