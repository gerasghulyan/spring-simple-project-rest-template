package com.vntana.core.rest.resource.organization;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest;
import com.vntana.core.model.organization.request.CreateOrganizationRequest;
import com.vntana.core.model.organization.request.OrganizationPaidOutsideStripeRequest;
import com.vntana.core.model.organization.response.CheckAvailableOrganizationSlugResultResponse;
import com.vntana.core.model.organization.response.create.CreateOrganizationResultResponse;
import com.vntana.core.model.organization.response.create.OrganizationPaidOutsideStripeResponse;
import com.vntana.core.model.organization.response.get.GetOrganizationBySlugResultResponse;
import com.vntana.core.model.organization.response.get.GetOrganizationByUuidResultResponse;
import com.vntana.core.model.organization.response.invitation.GetOrganizationInvitationByOrganizationResponse;
import com.vntana.core.model.organization.response.update.request.UpdateOrganizationRequest;
import com.vntana.core.model.organization.response.update.response.UpdateOrganizationResultResponse;
import com.vntana.core.model.user.response.UserOrganizationResponse;
import com.vntana.core.rest.facade.organization.OrganizationServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:32 PM
 */
@RestController
@RequestMapping(value = "/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationResource.class);

    private final OrganizationServiceFacade organizationServiceFacade;

    public OrganizationResource(final OrganizationServiceFacade organizationServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationServiceFacade = organizationServiceFacade;
    }

    @PostMapping(path = "/slug-availability")
    public ResponseEntity<CheckAvailableOrganizationSlugResultResponse> checkSlugAvailability(@RequestBody final CheckAvailableOrganizationSlugRequest request) {
        LOGGER.debug("Checking slug availability for request - {}", request);
        final CheckAvailableOrganizationSlugResultResponse resultResponse = organizationServiceFacade.checkSlugAvailability(request);
        LOGGER.debug("Successfully checked slug availability with response - {}", resultResponse);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<CreateOrganizationResultResponse> create(@RequestBody final CreateOrganizationRequest request) {
        LOGGER.debug("Creating organization for request - {}", request);
        final CreateOrganizationResultResponse resultResponse = organizationServiceFacade.create(request);
        LOGGER.debug("Successfully created organization with response - {}", resultResponse);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @GetMapping(path = "/users/{uuid}")
    public ResponseEntity<UserOrganizationResponse> getUserOrganizations(@PathVariable("uuid") final String uuid) {
        LOGGER.debug("Processing find organizations by user uuid - {}", uuid);
        final UserOrganizationResponse resultResponse = organizationServiceFacade.getUserOrganizations(uuid);
        LOGGER.debug("Successfully proceeded find organizations by user uuid with response - {}", resultResponse);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @GetMapping(path = "/super-admin-users/{uuid}")
    public ResponseEntity<UserOrganizationResponse> getSuperAdminUserOrganizations(@PathVariable("uuid") final String uuid) {
        LOGGER.debug("Processing find organizations by super admin user uuid - {}", uuid);
        final UserOrganizationResponse resultResponse = organizationServiceFacade.getSuperAdminUserOrganizations(uuid);
        LOGGER.debug("Successfully proceeded find organizations by super admin user uuid with response - {}", resultResponse);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @GetMapping(path = "/slug/{slug}")
    public ResponseEntity<GetOrganizationBySlugResultResponse> getBySlug(@PathVariable("slug") final String slug) {
        LOGGER.debug("Retrieving organization by slug - {}", slug);
        final GetOrganizationBySlugResultResponse response = organizationServiceFacade.getBySlug(slug);
        LOGGER.debug("Successfully retrieved organization with response - {}", response);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<GetOrganizationByUuidResultResponse> getByUuid(@PathVariable("uuid") final String uuid) {
        LOGGER.debug("Retrieving organization by uuid - {}", uuid);
        final GetOrganizationByUuidResultResponse response = organizationServiceFacade.getByUuid(uuid);
        LOGGER.debug("Successfully retrieved organization with response - {}", response);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @PutMapping
    public ResponseEntity<UpdateOrganizationResultResponse> update(@RequestBody final UpdateOrganizationRequest request) {
        LOGGER.debug("Processing organization resource update method for request - {}", request);
        final UpdateOrganizationResultResponse response = organizationServiceFacade.update(request);
        LOGGER.debug("Successfully processed organization resource update method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @GetMapping(path = "{organizationUuid}/organization-invitations/")
    public ResponseEntity<GetOrganizationInvitationByOrganizationResponse> getOrganizationInvitation(@PathVariable("organizationUuid") final String organizationUuid) {
        LOGGER.debug("Processing getOrganizationInvitation method for organizationUuid - {}", organizationUuid);
        final GetOrganizationInvitationByOrganizationResponse resultResponse = organizationServiceFacade.getOrganizationInvitation(organizationUuid);
        LOGGER.debug("Successfully processed getOrganizationInvitation method for organizationUuid - {}", organizationUuid);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PutMapping(path = "/payment-outside-stripe")
    public ResponseEntity<OrganizationPaidOutsideStripeResponse> setPaymentOutsideStripe(@RequestBody final OrganizationPaidOutsideStripeRequest request){
        LOGGER.debug("Processing organization resource setPaymentOutsideStripe method for request - {}", request);
        final OrganizationPaidOutsideStripeResponse response = organizationServiceFacade.setPaymentOutsideStripe(request);
        LOGGER.debug("Successfully processed organization resource setPaymentOutsideStripe method with response - {}", response);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @GetMapping(path = "/payment-outside-stripe/{uuid}")
    public ResponseEntity<OrganizationPaidOutsideStripeResponse> getPaymentOutsideStripe(@PathVariable("uuid") final String uuid){
        LOGGER.debug("Processing organization resource getPaymentOutsideStripe method for uuid - {}", uuid);
        final OrganizationPaidOutsideStripeResponse response = organizationServiceFacade.getIsPaidOutsideStripe(uuid);
        LOGGER.debug("Successfully processed organization resource getPaymentOutsideStripe method with response - {}", response);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }
}
