package com.vntana.core.rest.facade.token.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.ApplicationEvent;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 4:37 PM
 */
public class ResetPasswordTokenCreatedEvent extends ApplicationEvent {

    private final String uuid;

    public ResetPasswordTokenCreatedEvent(final Object source, final String uuid) {
        super(source);
        this.uuid = uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResetPasswordTokenCreatedEvent)) {
            return false;
        }
        final ResetPasswordTokenCreatedEvent that = (ResetPasswordTokenCreatedEvent) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }
}
