package com.vntana.core.listener.organization;

import com.vntana.commons.indexation.payload.AbstractEntityUuidAwareLifecyclePayload;
import com.vntana.commons.indexation.payload.EntityLifecycle;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/24/2020
 * Time: 6:52 PM
 */
public class OrganizationUuidAwareLifecyclePayload extends AbstractEntityUuidAwareLifecyclePayload {

    public OrganizationUuidAwareLifecyclePayload(final String uuid, final EntityLifecycle entityLifecycle) {
        super(uuid, entityLifecycle);
    }
}