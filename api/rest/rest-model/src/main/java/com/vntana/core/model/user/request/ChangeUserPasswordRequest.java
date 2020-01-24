package com.vntana.core.model.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 1/24/2020
 * Time: 10:24 AM
 */
public class ChangeUserPasswordRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("oldPassword")
    private String oldPassword;

    @JsonProperty("newPassword")
    private String newPassword;

    public ChangeUserPasswordRequest() {
    }

    public ChangeUserPasswordRequest(final String uuid, final String oldPassword, final String newPassword) {
        this.uuid = uuid;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        final List<UserErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(uuid)) {
            errors.add(UserErrorResponseModel.MISSING_UUID);
        }
        if (StringUtils.isBlank(oldPassword)) {
            errors.add(UserErrorResponseModel.MISSING_PASSWORD);
        }
        if (StringUtils.isBlank(newPassword)) {
            errors.add(UserErrorResponseModel.MISSING_PASSWORD);
        }
        return errors;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }
}