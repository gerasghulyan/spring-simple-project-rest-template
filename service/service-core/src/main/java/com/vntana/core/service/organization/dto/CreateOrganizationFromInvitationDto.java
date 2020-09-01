package com.vntana.core.service.organization.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:18 PM
 */
public class CreateOrganizationFromInvitationDto implements ServiceDto {

    private final String name;
    private final String slug;
    private final String organizationInvitationUuid;

    public CreateOrganizationFromInvitationDto(final String name, final String slug, final String organizationInvitationUuid) {
        Assert.hasText(name, "The name should not be null or empty");
        Assert.hasText(slug, "The slug should not be null or empty");
        Assert.hasText(organizationInvitationUuid, "The organizationInvitationUuid should not be null or empty");
        this.name = name;
        this.slug = slug;
        this.organizationInvitationUuid = organizationInvitationUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateOrganizationFromInvitationDto)) {
            return false;
        }
        final CreateOrganizationFromInvitationDto that = (CreateOrganizationFromInvitationDto) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(slug, that.slug)
                .append(organizationInvitationUuid, that.organizationInvitationUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(organizationInvitationUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", name)
                .append("slug", slug)
                .append("organizationInvitationUuid", organizationInvitationUuid)
                .toString();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getOrganizationInvitationUuid() {
        return organizationInvitationUuid;
    }
}
