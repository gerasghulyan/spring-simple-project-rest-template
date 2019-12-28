package com.vntana.core.service.organization.exception;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/28/19
 * Time: 4:22 PM
 */
public class OrganizationOwnerNotFoundException extends RuntimeException {

    private final String organizationUuid;

    public OrganizationOwnerNotFoundException(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }
}
