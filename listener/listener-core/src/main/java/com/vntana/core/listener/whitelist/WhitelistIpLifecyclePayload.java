package com.vntana.core.listener.whitelist;

import com.vntana.core.service.whitelist.mediator.dto.SaveWhitelistIpLifecycleDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan.
 * Date: 12/9/19
 * Time: 5:11 PM
 */
public class WhitelistIpLifecyclePayload {

    private final SaveWhitelistIpLifecycleDto dto;
    private final WhitelistIpLifecycle lifecycle;

    public WhitelistIpLifecyclePayload(final SaveWhitelistIpLifecycleDto dto, final WhitelistIpLifecycle lifecycle) {
        this.dto = dto;
        this.lifecycle = lifecycle;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhitelistIpLifecyclePayload)) {
            return false;
        }
        final WhitelistIpLifecyclePayload that = (WhitelistIpLifecyclePayload) o;
        return new EqualsBuilder()
                .append(dto, that.dto)
                .append(lifecycle, that.lifecycle)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(dto)
                .append(lifecycle)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dto", dto)
                .append("lifecycle", lifecycle)
                .toString();
    }

    public SaveWhitelistIpLifecycleDto getDto() {
        return dto;
    }

    public WhitelistIpLifecycle getLifecycle() {
        return lifecycle;
    }
}