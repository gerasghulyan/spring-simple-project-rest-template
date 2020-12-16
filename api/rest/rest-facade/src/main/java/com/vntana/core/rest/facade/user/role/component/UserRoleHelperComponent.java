package com.vntana.core.rest.facade.user.role.component;

import com.vntana.core.domain.user.AbstractClientOrganizationAwareUserRole;
import com.vntana.core.model.user.role.request.UpdateClientRoleRequest;
import com.vntana.core.model.user.role.request.UserUpdateOrganizationClientsRolesRequest;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 15.12.2020
 * Time: 12:31
 */
public interface UserRoleHelperComponent {

    List<AbstractClientOrganizationAwareUserRole> getVisibleUserClientOrganizations(final String organizationUuid,
                                                                                    final String requestedUserUuid,
                                                                                    final String userUuid);

    List<UpdateClientRoleRequest> fetchRevokeRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request);

    List<UpdateClientRoleRequest> fetchGrantRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request);
}
