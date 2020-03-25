package com.vntana.core.listener.client;

import com.vntana.commons.indexation.payload.AbstractEntityLifecyclePayload;
import com.vntana.commons.indexation.payload.EntityLifecycle;
import com.vntana.core.domain.client.ClientOrganization;

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