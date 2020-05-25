package com.vntana.core.domain.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Arthur Asatryan.
 * Date: 10/16/19
 * Time: 12:10 PM
 */
@Entity
@Table(name = "user_role_super_admin")
@DiscriminatorValue("SUPER_ADMIN_ROLE")
public class UserSuperAdminRole extends AbstractUserRole {

    UserSuperAdminRole() {
        super();
    }

    public UserSuperAdminRole(final User user) {
        super(user, UserRole.SUPER_ADMIN);
    }
}
