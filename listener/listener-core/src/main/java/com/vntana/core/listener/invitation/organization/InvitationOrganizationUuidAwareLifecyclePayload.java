package com.vntana.core.listener.invitation.organization;

import com.vntana.commons.indexation.payload.AbstractEntityUuidAwareLifecyclePayload;
import com.vntana.commons.indexation.payload.EntityLifecycle;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 5:26 PM
 */
public class InvitationOrganizationUuidAwareLifecyclePayload extends AbstractEntityUuidAwareLifecyclePayload {

    public InvitationOrganizationUuidAwareLifecyclePayload(final String entityUuid, final EntityLifecycle entityLifecycle) {
        super(entityUuid, entityLifecycle);
    }
}
