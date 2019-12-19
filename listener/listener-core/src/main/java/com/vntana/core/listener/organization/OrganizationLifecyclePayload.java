package com.vntana.core.listener.organization;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.listener.commons.AbstractEntityLifecyclePayload;
import com.vntana.core.listener.commons.EntityLifecycle;

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
