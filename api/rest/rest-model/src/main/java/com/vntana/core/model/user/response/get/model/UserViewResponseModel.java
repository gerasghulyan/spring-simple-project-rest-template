package com.vntana.core.model.user.response.get.model;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:50 PM
 */
public class UserViewResponseModel extends AbstractUuidAwareResponseModel {

    private String fullName;

    public UserViewResponseModel() {
        super();
    }

    public UserViewResponseModel(final String uuid, final String fullName) {
        super(uuid);
        this.fullName = fullName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserViewResponseModel)) {
            return false;
        }
        final UserViewResponseModel that = (UserViewResponseModel) o;
        return new EqualsBuilder()
                .append(fullName, that.fullName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(fullName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("fullName", fullName)
                .toString();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

}
