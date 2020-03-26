package com.vntana.core.listener.organization;

import com.vntana.commons.indexation.payload.AbstractEntityLifecyclePayload;
import com.vntana.commons.indexation.payload.EntityLifecycle;
import com.vntana.core.domain.organization.Organization;

/**
 * Created by Geras Ghulyan.
 * Date: 12/9/19
 * Time: 5:11 PM
 */
public class OrganizationLifecyclePayload extends AbstractEntityLifecyclePayload<Organization> {

    public OrganizationLifecyclePayload(final Organization organization, final EntityLifecycle entityLifecycle) {
        super(organization, entityLifecycle);
    }
}
