package com.vntana.core.listener.client;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.listener.commons.AbstractEntityLifecyclePayload;
import com.vntana.core.listener.commons.EntityLifecycle;

/**
 * Created by Geras Ghulyan
 * Date: 11.02.20
 * Time: 12:06
 */
public class ClientOrganizationLifecyclePayload extends AbstractEntityLifecyclePayload<ClientOrganization> {

    public ClientOrganizationLifecyclePayload(final ClientOrganization clientOrganization, final EntityLifecycle entityLifecycle) {
        super(clientOrganization, entityLifecycle);
    }
}