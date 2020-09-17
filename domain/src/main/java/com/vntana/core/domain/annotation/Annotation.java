package com.vntana.core.domain.annotation;

import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static com.vntana.commons.persistence.domain.DBConstants.BIG_TEXT_LENGTH;

/**
 * Created by Vardan Aivazian
 * Date: 14.09.2020
 * Time: 16:45
 */
@Entity
@Table(name = "annotation", uniqueConstraints = {
        @UniqueConstraint(name = "uk_annotation_uuid", columnNames = {"uuid"})
})
public class Annotation extends AbstractUuidAwareDomainEntity {

    @Column(name = "product_uuid", nullable = false, updatable = false)
    private String productUuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_annotation_user_id"), updatable = false)
    private User user;
    
    @Column(name = "text", nullable = false, length = BIG_TEXT_LENGTH)
    private String text;
    
    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "resolved", nullable = false)
    private Boolean resolved;

    @Column(name = "d1", nullable = false)
    private Double d1;
    
    @Column(name = "d2", nullable = false)
    private Double d2;
    
    @Column(name = "d3", nullable = false)
    private Double d3;
    
    public Annotation() {
        super();
    }

    public Annotation(final String productUuid, final User user, final String text, final Integer number, final Boolean resolved, final Double d1, final Double d2, final Double d3) {
        this.productUuid = productUuid;
        this.user = user;
        this.text = text;
        this.number = number;
        this.resolved = resolved;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Annotation)) {
            return false;
        }
        final Annotation that = (Annotation) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(productUuid, that.productUuid)
                .append(getIdOrNull(user), getIdOrNull(that.user))
                .append(text, that.text)
                .append(number, that.number)
                .append(resolved, that.resolved)
                .append(d1, that.d1)
                .append(d2, that.d2)
                .append(d3, that.d3)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(productUuid)
                .append(getIdOrNull(user))
                .append(text)
                .append(number)
                .append(resolved)
                .append(d1)
                .append(d2)
                .append(d3)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("productUuid", productUuid)
                .append("user", getIdOrNull(user))
                .append("text", text)
                .append("number", number)
                .append("resolved", resolved)
                .append("d1", d1)
                .append("d2", d2)
                .append("d3", d3)
                .toString();
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(final String productUuid) {
        this.productUuid = productUuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(final Boolean resolved) {
        this.resolved = resolved;
    }

    public Double getD1() {
        return d1;
    }

    public void setD1(final Double d1) {
        this.d1 = d1;
    }

    public Double getD2() {
        return d2;
    }

    public void setD2(final Double d2) {
        this.d2 = d2;
    }

    public Double getD3() {
        return d3;
    }

    public void setD3(final Double d3) {
        this.d3 = d3;
    }
}
