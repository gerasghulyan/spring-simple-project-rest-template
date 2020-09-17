package com.vntana.core.model.annotation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 11:56
 */
public class UpdateAnnotationResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("text")
    private String text;
    
    @JsonProperty("resolved")
    private Boolean resolved;

    @JsonProperty("d1")
    private Double d1;

    @JsonProperty("d2")
    private Double d2;

    @JsonProperty("d3")
    private Double d3;

    public UpdateAnnotationResponseModel() {
        super();
    }

    public UpdateAnnotationResponseModel(final String uuid, final String text, final Boolean resolved, final Double d1, final Double d2, final Double d3) {
        super(uuid);
        this.text = text;
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
        if (!(o instanceof UpdateAnnotationResponseModel)) {
            return false;
        }
        final UpdateAnnotationResponseModel that = (UpdateAnnotationResponseModel) o;
        return new EqualsBuilder()
                .append(text, that.text)
                .append(resolved, that.resolved)
                .append(d1, that.d1)
                .append(d2, that.d2)
                .append(d3, that.d3)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(text)
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
                .append("text", text)
                .append("resolved", resolved)
                .append("d1", d1)
                .append("d2", d2)
                .append("d3", d3)
                .toString();
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
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
