package com.vntana.core.domain.template.email;

import com.vntana.core.domain.commons.AbstractUuidAwareDomainEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 10:29 AM
 */
@Entity
@Table(name = "template_email", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"type", "template_name"}, name = "UK_template_email_type_template_name")
})
public class TemplateEmail extends AbstractUuidAwareDomainEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TemplateEmailType type;

    @Column(name = "template_name", nullable = false)
    private String templateName;

    public TemplateEmail() {
        super();
    }

    public TemplateEmail(final TemplateEmailType type, final String templateName) {
        super();
        this.type = type;
        this.templateName = templateName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateEmail)) {
            return false;
        }
        final TemplateEmail that = (TemplateEmail) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(type, that.type)
                .append(templateName, that.templateName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(type)
                .append(templateName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("templateName", templateName)
                .toString();
    }

    public TemplateEmailType getType() {
        return type;
    }

    public void setType(final TemplateEmailType type) {
        this.type = type;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }
}
