package com.vntana.core.persistence.user.impl.role;

import com.vntana.core.domain.user.AbstractUserRole;
import com.vntana.core.persistence.user.role.UserRoleRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 04.11.2020
 * Time: 16:09
 */
@Repository
public class UserRoleRepositoryImpl implements UserRoleRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleRepositoryImpl.class);
    private final EntityManager entityManager;

    public UserRoleRepositoryImpl(final EntityManager entityManager) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.entityManager = entityManager;
    }

    @Query("select role from AbstractUserRole role where role.id in " +
            "(select aur.id from UserClientAdminRole ucar join AbstractUserRole aur on aur.id = ucar.id where ucar.clientOrganization.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientContentManagerRole uccmr join AbstractUserRole aur on aur.id = uccmr.id where uccmr.clientOrganization.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserClientViewerRole ucvr join AbstractUserRole aur on aur.id = ucvr.id where ucvr.clientOrganization.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserOrganizationOwnerRole uoor join AbstractUserRole aur on aur.id = uoor.id where uoor.organization.uuid = :organizationUuid)" +
            " or role.id in " +
            "(select aur.id from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.organization.uuid = :organizationUuid)")
    @Override
    public List<AbstractUserRole> findAllByOrganizationUuid(final String organizationUuid) {
        /*entityManager.createQuery(
                "select role from user_role role where role.id in" +
                        "(select aur.id from user_role_client_admin ucar join user_role aur on aur.id = ucar.id where ucar.clientOrganization.organization.uuid = "+organizationUuid+")",
                AbstractUserRole.class);*/
        return null;
    }

    @Override
    public Optional<AbstractUserRole> findAllByOrganizationAndUser(final String organizationUuid, final String userUuid) {
        return Optional.empty();
    }
}
