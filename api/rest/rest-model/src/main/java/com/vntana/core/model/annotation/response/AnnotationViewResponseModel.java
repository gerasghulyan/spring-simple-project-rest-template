package com.vntana.core.model.annotation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import com.vntana.core.model.user.response.get.model.UserViewResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 14:21
 */
public class AnnotationViewResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("productUuid")
    private String productUuid;

    @JsonProperty("text")
    private String text;

    @JsonProperty("owner")
    private UserViewResponseModel owner;

    @JsonProperty("number")
    private Integer number;

    @JsonProperty("resolved")
    private Boolean resolved;

    @JsonProperty("d1")
    private Double d1;

    @JsonProperty("d2")
    private Double d2;

    @JsonProperty("d3")
    private Double d3;
    
    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("updated")
    private LocalDateTime updated;

    public AnnotationViewResponseModel() {
        super();
    }

    public AnnotationViewResponseModel(final String uuid, final String productUuid, final String text, final UserViewResponseModel owner, final Integer number, final Boolean resolved, final Double d1, final Double d2, final Double d3, final LocalDateTime created, final LocalDateTime updated) {
        super(uuid);
        this.productUuid = productUuid;
        this.text = text;
        this.owner = owner;
        this.number = number;
        this.resolved = resolved;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.created = created;
        this.updated = updated;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnotationViewResponseModel)) {
            return false;
        }
        final AnnotationViewResponseModel that = (AnnotationViewResponseModel) o;
        return new EqualsBuilder()
                .append(productUuid, that.productUuid)
                .append(text, that.text)
                .append(owner, that.owner)
                .append(number, that.number)
                .append(resolved, that.resolved)
                .append(d1, that.d1)
                .append(d2, that.d2)
                .append(d3, that.d3)
                .append(created, that.created)
                .append(updated, that.updated)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(productUuid)
                .append(text)
                .append(owner)
                .append(number)
                .append(resolved)
                .append(d1)
                .append(d2)
                .append(d3)
                .append(created)
                .append(updated)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("productUuid", productUuid)
                .append("text", text)
                .append("owner", owner)
                .append("number", number)
                .append("resolved", resolved)
                .append("d1", d1)
                .append("d2", d2)
                .append("d3", d3)
                .append("created", created)
                .append("updated", updated)
                .toString();
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(final String productUuid) {
        this.productUuid = productUuid;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public UserViewResponseModel getOwner() {
        return owner;
    }

    public void setOwner(final UserViewResponseModel owner) {
        this.owner = owner;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(final LocalDateTime updated) {
        this.updated = updated;
    }
}
