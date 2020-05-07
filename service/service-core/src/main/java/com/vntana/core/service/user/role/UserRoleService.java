package com.vntana.core.service.user.role;

import com.vntana.core.domain.user.AbstractUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 12:47 PM
 */
@Repository
public interface UserRoleService {

    List<AbstractUserRole> findAllByOrganizationUuid(final String organizationUuid);
}
