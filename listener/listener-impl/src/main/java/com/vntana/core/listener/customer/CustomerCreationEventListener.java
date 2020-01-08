package com.vntana.core.listener.customer;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.listener.commons.EntityLifecycle;
import com.vntana.core.listener.commons.EntityLifecycleListener;
import com.vntana.core.listener.organization.OrganizationLifecyclePayload;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.payment.client.customer.PaymentCustomerResourceClient;
import com.vntana.payment.reset.model.customer.create.request.CustomerCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/28/19
 * Time: 11:22 AM
 */
@Component
@Lazy(false)
public class CustomerCreationEventListener implements EntityLifecycleListener<Organization, OrganizationLifecyclePayload> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCreationEventListener.class);

    private final OrganizationService organizationService;
    private final PaymentCustomerResourceClient customerResourceClient;

    public CustomerCreationEventListener(final OrganizationService organizationService, final PaymentCustomerResourceClient customerResourceClient) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationService = organizationService;
        this.customerResourceClient = customerResourceClient;
    }

    @EventListener
    @Override
    public void handleEvent(final OrganizationLifecyclePayload payload) {
        Assert.notNull(payload, "The OrganizationLifecyclePayload should not be null");
        LOGGER.debug("Handling organization creation event to create payment customer for organization having uuid - {}", payload.entity().getUuid());
        if (payload.lifecycle() != EntityLifecycle.CREATED) {
            return;
        }
        final Organization organization = payload.entity();
        final String ownerEmail = organizationService.getOrganizationOwnerEmail(organization.getUuid());
        customerResourceClient.create(new CustomerCreateRequest(organization.getUuid(), ownerEmail));
        LOGGER.debug("Successfully handling organization creation event to create payment customer for organization having uuid - {}", payload.entity().getUuid());
    }
}
