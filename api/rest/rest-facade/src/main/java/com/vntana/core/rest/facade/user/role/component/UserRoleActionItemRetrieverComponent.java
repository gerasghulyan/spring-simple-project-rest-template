package com.vntana.core.rest.facade.user.role.component;

import com.vntana.core.model.user.role.request.UpdateClientRoleRequest;
import com.vntana.core.model.user.role.request.UpdatedClientRoleRequestModel;
import com.vntana.core.model.user.role.request.UserUpdateOrganizationClientsRolesRequest;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 15.12.2020
 * Time: 12:31
 */
public interface UserRoleActionItemRetrieverComponent {

    List<UpdateClientRoleRequest> fetchRevokedRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request);

    List<UpdateClientRoleRequest> fetchGrantedRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request);
    
    List<UpdatedClientRoleRequestModel> fetchUpdatedRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request);
}
