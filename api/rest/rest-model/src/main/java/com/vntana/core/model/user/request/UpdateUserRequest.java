package com.vntana.core.model.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 4:05 PM
 */
public class UpdateUserRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    @JsonProperty("name")
    private String fullName;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(final String uuid, final String imageBlobId, final String fullName) {
        this.uuid = uuid;
        this.imageBlobId = imageBlobId;
        this.fullName = fullName;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(uuid)) {
            errors.add(UserErrorResponseModel.MISSING_UUID);
        }
        if (StringUtils.isBlank(fullName)) {
            errors.add(UserErrorResponseModel.MISSING_FULL_NAME);
        }
        return errors;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }
}