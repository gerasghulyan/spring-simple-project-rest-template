package com.vntana.core.service.organization.exception;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.service.common.exception.EntityNotFoundForUuidException;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 10:57 AM
 */
public class OrganizationNotFoundForUuidException extends EntityNotFoundForUuidException {
    
    public OrganizationNotFoundForUuidException(final String uuid) {
        super(uuid, Organization.class);
    }
}