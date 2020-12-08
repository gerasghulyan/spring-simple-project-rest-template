package com.vntana.core.service.organization.exception;

import com.vntana.commons.service.exception.EntityNotFoundForUuidException;
import com.vntana.core.domain.organization.Organization;

/**
 * Created by Vardan Aivazian
 * Date: 07.12.2020
 * Time: 17:31
 */
public class OrganizationNotFoundForClientUuidException extends EntityNotFoundForUuidException {

    public OrganizationNotFoundForClientUuidException(final String clientUuid) {
        super(clientUuid, Organization.class);
    }
}