package com.vntana.core.listener.customer.update;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.listener.commons.EntityLifecycle;
import com.vntana.core.listener.commons.EntityLifecycleListener;
import com.vntana.core.listener.organization.OrganizationLifecyclePayload;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.payment.client.customer.PaymentCustomerResourceClient;
import com.vntana.payment.reset.model.customer.update.request.CustomerUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static java.lang.String.format;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/6/2020
 * Time: 4:25 PM
 */
@Component
@Lazy(false)
public class CustomerUpdateEventListener implements EntityLifecycleListener<Organization, OrganizationLifecyclePayload> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerUpdateEventListener.class);

    private final OrganizationService organizationService;
    private final UserService userService;
    private final PaymentCustomerResourceClient customerResourceClient;

    public CustomerUpdateEventListener(final OrganizationService organizationService, final UserService userService, final PaymentCustomerResourceClient customerResourceClient) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationService = organizationService;
        this.userService = userService;
        this.customerResourceClient = customerResourceClient;
    }

    @EventListener
    @Override
    public void handleEvent(final OrganizationLifecyclePayload payload) {
        Assert.notNull(payload, "The OrganizationLifecyclePayload should not be null");
        LOGGER.debug("Handling organization update event to update payment customer for organization having uuid - {}", payload.entity().getUuid());
        if (payload.lifecycle() != EntityLifecycle.UPDATED) {
            return;
        }
        final Organization organization = payload.entity();
        final String ownerEmail = organizationService.getOrganizationOwnerEmail(organization.getUuid());
        final User user = userService.findByEmail(ownerEmail)
                .orElseThrow(() -> new IllegalStateException(format("Can not find user for email - %s", ownerEmail)));
        customerResourceClient.update(new CustomerUpdateRequest(
                        organization.getUuid(),
                        user.getFullName(),
                        organization.getName()
                )
        );
        LOGGER.debug("Successfully handling organization update event to update payment customer for organization having uuid - {}", payload.entity().getUuid());
    }
}