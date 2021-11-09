package com.project.sample.service.organization.exception;


import com.project.sample.commons.persistence.domain.organization.Organization;
import com.project.sample.commons.service.exception.EntityNotFoundForUuidException;

/**
 * Created by Geras Ghulyan.
 * Date: 11/11/19
 * Time: 10:57 AM
 */
public class OrganizationNotFoundForUuidException extends EntityNotFoundForUuidException {
    
    public OrganizationNotFoundForUuidException(final String uuid) {
        super(uuid, Organization.class);
    }
}