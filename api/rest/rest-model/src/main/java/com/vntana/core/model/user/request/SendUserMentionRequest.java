package com.vntana.core.model.user.request;

import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.user.enums.UserMentionedEntityTypeModel;
import com.vntana.core.model.user.error.UserErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vardan Aivazian
 * Date: 07.10.2020
 * Time: 11:36
 */
public class SendUserMentionRequest extends AbstractRequestModel implements ValidatableRequest<UserErrorResponseModel> {

    private final String email;
    private final String promptingUserName;
    private final String mentionedUserName;
    private final UserMentionedEntityTypeModel entityType;
    private final String entityUuid;
    private final String productUuid;
    private final String productName;
    private final String clientSlug;
    private final String organizationSlug;

    public SendUserMentionRequest(final String email,
                                  final String promptingUserName,
                                  final String mentionedUserName,
                                  final UserMentionedEntityTypeModel entityType,
                                  final String entityUuid,
                                  final String productUuid,
                                  final String productName,
                                  final String clientSlug,
                                  final String organizationSlug) {
        this.email = email;
        this.promptingUserName = promptingUserName;
        this.mentionedUserName = mentionedUserName;
        this.entityType = entityType;
        this.entityUuid = entityUuid;
        this.productUuid = productUuid;
        this.productName = productName;
        this.clientSlug = clientSlug;
        this.organizationSlug = organizationSlug;
    }

    @Override
    public List<UserErrorResponseModel> validate() {
        if (StringUtils.isEmpty(email)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_EMAIL);
        }
        if (StringUtils.isEmpty(promptingUserName)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_PROMPTING_USER_NAME);
        }
        if (StringUtils.isEmpty(mentionedUserName)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_MENTIONED_USER_NAME);
        }
        if (Objects.isNull(entityType)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_ENTITY_TYPE);
        }
        if (StringUtils.isEmpty(entityUuid)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_ENTITY_UUID);
        }
        if (StringUtils.isEmpty(productName)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_PRODUCT_NAME);
        }
        if (StringUtils.isEmpty(clientSlug)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_CLIENT_SLUG);
        }
        if (StringUtils.isEmpty(organizationSlug)) {
            return Collections.singletonList(UserErrorResponseModel.MISSING_ORGANIZATION_SLUG);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SendUserMentionRequest)) {
            return false;
        }
        final SendUserMentionRequest that = (SendUserMentionRequest) o;
        return new EqualsBuilder()
                .append(email, that.email)
                .append(promptingUserName, that.promptingUserName)
                .append(mentionedUserName, that.mentionedUserName)
                .append(entityType, that.entityType)
                .append(entityUuid, that.entityUuid)
                .append(productUuid, that.productUuid)
                .append(productName, that.productName)
                .append(clientSlug, that.clientSlug)
                .append(organizationSlug, that.organizationSlug)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .append(promptingUserName)
                .append(mentionedUserName)
                .append(entityType)
                .append(entityUuid)
                .append(productUuid)
                .append(productName)
                .append(clientSlug)
                .append(organizationSlug)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("email", email)
                .append("promptingUserName", promptingUserName)
                .append("mentionedUserName", mentionedUserName)
                .append("entityType", entityType)
                .append("entityUuid", entityUuid)
                .append("productUuid", productUuid)
                .append("productName", productName)
                .append("clientSlug", clientSlug)
                .append("organizationSlug", organizationSlug)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public String getPromptingUserName() {
        return promptingUserName;
    }

    public String getMentionedUserName() {
        return mentionedUserName;
    }

    public UserMentionedEntityTypeModel getEntityType() {
        return entityType;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public String getProductUuid() {
        return productUuid;
    }

    public String getProductName() {
        return productName;
    }

    public String getClientSlug() {
        return clientSlug;
    }

    public String getOrganizationSlug() {
        return organizationSlug;
    }
}
