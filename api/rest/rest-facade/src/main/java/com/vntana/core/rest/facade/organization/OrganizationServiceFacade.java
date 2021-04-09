package com.vntana.core.rest.facade.organization;

import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest;
import com.vntana.core.model.organization.request.CreateOrganizationRequest;
import com.vntana.core.model.organization.request.GetAllOrganizationsRequest;
import com.vntana.core.model.organization.request.OrganizationPaidOutsideStripeRequest;
import com.vntana.core.model.organization.response.CheckAvailableOrganizationSlugResultResponse;
import com.vntana.core.model.organization.response.create.CreateOrganizationResultResponse;
import com.vntana.core.model.organization.response.create.OrganizationPaidOutsideStripeResponse;
import com.vntana.core.model.organization.response.get.GetAllOrganizationResponse;
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

    OrganizationPaidOutsideStripeResponse setPaymentOutsideStripe(final OrganizationPaidOutsideStripeRequest request);

    OrganizationPaidOutsideStripeResponse getIsPaidOutsideStripe(final String uuid);

    GetAllOrganizationResponse getAllOrganizations(final GetAllOrganizationsRequest request);
}
