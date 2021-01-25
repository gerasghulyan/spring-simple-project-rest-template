package com.vntana.core.rest.facade.organization;

import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest;
import com.vntana.core.model.organization.request.CreateOrganizationRequest;
import com.vntana.core.model.organization.request.OrganizationPayedOutsideStripeRequest;
import com.vntana.core.model.organization.response.CheckAvailableOrganizationSlugResultResponse;
import com.vntana.core.model.organization.response.create.CreateOrganizationResultResponse;
import com.vntana.core.model.organization.response.create.OrganizationPayedOutsideStripeResponse;
import com.vntana.core.model.organization.response.get.GetOrganizationBySlugResultResponse;
import com.vntana.core.model.organization.response.get.GetOrganizationByUuidResultResponse;
import com.vntana.core.model.organization.response.invitation.GetOrganizationInvitationByOrganizationResponse;
import com.vntana.core.model.organization.response.update.request.UpdateOrganizationRequest;
import com.vntana.core.model.organization.response.update.response.UpdateOrganizationResultResponse;
import com.vntana.core.model.user.response.UserOrganizationResponse;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:03 PM
 */
public interface OrganizationServiceFacade {
    CheckAvailableOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableOrganizationSlugRequest request);

    CreateOrganizationResultResponse create(final CreateOrganizationRequest request);

    UserOrganizationResponse getUserOrganizations(final String userUuid);

    UserOrganizationResponse getSuperAdminUserOrganizations(final String userUuid);

    GetOrganizationBySlugResultResponse getBySlug(final String slug);

    GetOrganizationByUuidResultResponse getByUuid(final String uuid);

    UpdateOrganizationResultResponse update(final UpdateOrganizationRequest request);

    GetOrganizationInvitationByOrganizationResponse getOrganizationInvitation(final String uuid);

    OrganizationPayedOutsideStripeResponse setPaymentOutsideStripe(final OrganizationPayedOutsideStripeRequest request);

    OrganizationPayedOutsideStripeResponse getIsPayedOutsideStripe(final String uuid);
}
