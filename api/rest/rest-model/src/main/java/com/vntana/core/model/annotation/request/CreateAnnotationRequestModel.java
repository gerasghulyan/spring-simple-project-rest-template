package com.vntana.core.model.annotation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 18:01
 */
public class CreateAnnotationRequestModel extends AbstractRequestModel implements ValidatableRequest<AnnotationErrorResponseModel> {

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("productUuid")
    private String productUuid;

    @JsonProperty("text")
    private String text;

    @JsonProperty("number")
    private Integer number;
    
    @JsonProperty("d1")
    private Double d1;

    @JsonProperty("d2")
    private Double d2;

    @JsonProperty("d3")
    private Double d3;

    public CreateAnnotationRequestModel() {
        super();
    }

    public CreateAnnotationRequestModel(final String userUuid, final String productUuid, final String text, final Integer number, final Double d1, final Double d2, final Double d3) {
        this.userUuid = userUuid;
        this.productUuid = productUuid;
        this.text = text;
        this.number = number;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    @Override
    public List<AnnotationErrorResponseModel> validate() {
        final List<AnnotationErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isEmpty(userUuid)) {
            errors.add(AnnotationErrorResponseModel.MISSING_USER_UUID);
        }
        if (StringUtils.isEmpty(productUuid)) {
            errors.add(AnnotationErrorResponseModel.MISSING_PRODUCT_UUID);
        }
        if (StringUtils.isEmpty(text)) {
            errors.add(AnnotationErrorResponseModel.MISSING_TEXT);
        }
        if (Objects.isNull(number)) {
            errors.add(AnnotationErrorResponseModel.MISSING_NUMBER);
        }
        if (Objects.nonNull(number) && number < 1) {
            errors.add(AnnotationErrorResponseModel.NOT_POSITIVE_NUMBER);
        }
        if (!ObjectUtils.allNotNull(d1, d2, d3)) {
            errors.add(AnnotationErrorResponseModel.MISSING_DIMENSION);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateAnnotationRequestModel)) {
            return false;
        }
        final CreateAnnotationRequestModel that = (CreateAnnotationRequestModel) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(productUuid, that.productUuid)
                .append(text, that.text)
                .append(number, that.number)
                .append(d1, that.d1)
                .append(d2, that.d2)
                .append(d3, that.d3)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(productUuid)
                .append(text)
                .append(number)
                .append(d1)
                .append(d2)
                .append(d3)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("productUuid", productUuid)
                .append("text", text)
                .append("number", number)
                .append("d1", d1)
                .append("d2", d2)
                .append("d3", d3)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
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
