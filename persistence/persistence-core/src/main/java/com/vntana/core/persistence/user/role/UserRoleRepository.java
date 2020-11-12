package com.vntana.core.persistence.user.role;

import com.vntana.core.domain.user.AbstractUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 12:44 PM
 */
public interface UserRoleRepository extends JpaRepository<AbstractUserRole, Long>, UserRoleRepositoryCustom {
}
