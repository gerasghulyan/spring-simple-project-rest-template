package com.vntana.core.model.annotation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 11:27
 */
public class UpdateAnnotationRequestModel extends AbstractRequestModel implements ValidatableRequest<AnnotationErrorResponseModel> {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("text")
    private String text;

    @JsonProperty("d1")
    private Double d1;

    @JsonProperty("d2")
    private Double d2;

    @JsonProperty("d3")
    private Double d3;

    public UpdateAnnotationRequestModel() {
        super();
    }

    public UpdateAnnotationRequestModel(final String uuid, final String userUuid, final String text, final Double d1, final Double d2, final Double d3) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.text = text;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    @Override
    public List<AnnotationErrorResponseModel> validate() {
        final List<AnnotationErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isEmpty(uuid)) {
            errors.add(AnnotationErrorResponseModel.MISSING_UUID);
        }
        if (StringUtils.isEmpty(userUuid)) {
            errors.add(AnnotationErrorResponseModel.MISSING_USER_UUID);
        }
        if (StringUtils.isEmpty(text)) {
            errors.add(AnnotationErrorResponseModel.MISSING_TEXT);
        }
        if (d1 == null || d2 == null || d3 == null) {
            errors.add(AnnotationErrorResponseModel.MISSING_DIMENSION);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateAnnotationRequestModel)) {
            return false;
        }
        final UpdateAnnotationRequestModel that = (UpdateAnnotationRequestModel) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(userUuid, that.userUuid)
                .append(text, that.text)
                .append(d1, that.d1)
                .append(d2, that.d2)
                .append(d3, that.d3)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(userUuid)
                .append(text)
                .append(d1)
                .append(d2)
                .append(d3)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuid", uuid)
                .append("userUuid", userUuid)
                .append("text", text)
                .append("d1", d1)
                .append("d2", d2)
                .append("d3", d3)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
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
