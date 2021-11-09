package com.project.sample.service.organization.dto;

import com.project.sample.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 5:18 PM
 */
public class CreateOrganizationDto implements ServiceDto {

    private String name;

    public CreateOrganizationDto() {
        super();
    }

    public CreateOrganizationDto(final String name) {
        Assert.hasText(name, "The name should not be null or empty");
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateOrganizationDto)) {
            return false;
        }
        final CreateOrganizationDto that = (CreateOrganizationDto) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }

    public String getName() {
        return StringUtils.trim(name);
    }
}
