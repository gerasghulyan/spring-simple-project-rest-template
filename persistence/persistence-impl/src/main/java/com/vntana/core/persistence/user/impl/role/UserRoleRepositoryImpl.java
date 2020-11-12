package com.vntana.core.persistence.user.impl.role;

import com.vntana.core.domain.user.AbstractUserRole;
import com.vntana.core.persistence.user.role.UserRoleRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.vntana.core.persistence.user.impl.util.helper.RepositoryHelper.find;

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

    @Override
    public List<AbstractUserRole> findAllByOrganization(final String organizationUuid) {
        return entityManager.createQuery(
                "select role from AbstractUserRole role where role.id in " +
                        "(select aur.id from UserOrganizationOwnerRole uoor join AbstractUserRole aur on aur.id = uoor.id where uoor.organization.uuid = :organizationUuid)" +
                        " or role.id in " +
                        "(select aur.id from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.organization.uuid = :organizationUuid)",
                AbstractUserRole.class)
                .setParameter("organizationUuid", organizationUuid)
                .getResultList();
    }

    @Override
    public Optional<AbstractUserRole> findByOrganizationAndUser(final String organizationUuid, final String userUuid) {
        return find(() ->
                entityManager.createQuery(
                        "select role from AbstractUserRole role where role.id in " +
                                "(select aur.id from UserOrganizationOwnerRole uoor join AbstractUserRole aur on aur.id = uoor.id where uoor.organization.uuid = :organizationUuid and aur.user.uuid = :userUuid)" +
                                " or role.id in " +
                                "(select aur.id from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id where uoar.organization.uuid = :organizationUuid and aur.user.uuid = :userUuid)",
                        AbstractUserRole.class)
                        .setParameter("organizationUuid", organizationUuid)
                        .setParameter("userUuid", userUuid)
                        .getSingleResult()
        );
    }

    @Override
    public List<AbstractUserRole> findAllOrganizationClientsByOrganization(final String organizationUuid) {
        return entityManager.createQuery(
                "select role from AbstractUserRole role where role.id in " +
                        "(select aur.id from UserClientOrganizationAdminRole ucar join AbstractUserRole aur on aur.id = ucar.id where ucar.clientOrganization.organization.uuid = :organizationUuid)" +
                        " or role.id in " +
                        "(select aur.id from UserClientOrganizationContentManagerRole uccmr join AbstractUserRole aur on aur.id = uccmr.id where uccmr.clientOrganization.organization.uuid = :organizationUuid)" +
                        " or role.id in " +
                        "(select aur.id from UserClientOrganizationViewerRole ucvr join AbstractUserRole aur on aur.id = ucvr.id where ucvr.clientOrganization.organization.uuid = :organizationUuid)",
                AbstractUserRole.class)
                .setParameter("organizationUuid", organizationUuid)
                .getResultList();
    }

    @Override
    public List<AbstractUserRole> findAllOrganizationClientsRolesByOrganizationAndUser(final String organizationUuid, final String userUuid) {
        return entityManager.createQuery(
                "select role from AbstractUserRole role where role.id in " +
                        "(select aur.id from UserClientOrganizationAdminRole ucar join AbstractUserRole aur on aur.id = ucar.id where ucar.clientOrganization.organization.uuid = :organizationUuid and ucar.user.uuid = :userUuid)" +
                        " or role.id in " +
                        "(select aur.id from UserClientOrganizationContentManagerRole uccmr join AbstractUserRole aur on aur.id = uccmr.id where uccmr.clientOrganization.organization.uuid = :organizationUuid and uccmr.user.uuid = :userUuid)" +
                        " or role.id in " +
                        "(select aur.id from UserClientOrganizationViewerRole ucvr join AbstractUserRole aur on aur.id = ucvr.id where ucvr.clientOrganization.organization.uuid = :organizationUuid and ucvr.user.uuid = :userUuid)",
                AbstractUserRole.class)
                .setParameter("organizationUuid", organizationUuid)
                .setParameter("userUuid", userUuid)
                .getResultList();
    }

    @Override
    public Optional<AbstractUserRole> findByClientOrganizationAndUser(final String clientOrganizationUuid, final String userUuid) {
        return find(() ->
                entityManager.createQuery(
                        "select role from AbstractUserRole role where role.id in " +
                                "(select aur.id from UserClientOrganizationAdminRole ucar join AbstractUserRole aur on aur.id = ucar.id where ucar.clientOrganization.uuid = :clientOrganizationUuid and ucar.user.uuid = :userUuid)" +
                                " or role.id in " +
                                "(select aur.id from UserClientOrganizationContentManagerRole uccmr join AbstractUserRole aur on aur.id = uccmr.id where uccmr.clientOrganization.uuid = :clientOrganizationUuid and uccmr.user.uuid = :userUuid)" +
                                " or role.id in " +
                                "(select aur.id from UserClientOrganizationViewerRole ucvr join AbstractUserRole aur on aur.id = ucvr.id where ucvr.clientOrganization.uuid = :clientOrganizationUuid and ucvr.user.uuid = :userUuid)",
                        AbstractUserRole.class)
                        .setParameter("clientOrganizationUuid", clientOrganizationUuid)
                        .setParameter("userUuid", userUuid)
                        .getSingleResult()
        );
    }

    @Override
    public Optional<AbstractUserRole> findAdminRoleByUserAndOrganization(final String userUuid, final String organizationUuid) {
        return find(() ->
                entityManager.createQuery(
                        "select aur from UserOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id " +
                                "where uoar.organization.uuid = :organizationUuid and uoar.user.uuid = :userUuid",
                        AbstractUserRole.class)
                        .setParameter("organizationUuid", organizationUuid)
                        .setParameter("userUuid", userUuid)
                        .getSingleResult()
        );
    }

    @Override
    public Optional<AbstractUserRole> findClientAdminRoleByUserAndClientOrganization(final String userUuid, final String clientOrganizationUuid) {
        return find(() ->
                entityManager.createQuery(
                        "select aur from UserClientOrganizationAdminRole uoar join AbstractUserRole aur on aur.id = uoar.id " +
                                "where uoar.clientOrganization.uuid = :clientOrganizationUuid and uoar.user.uuid = :userUuid",
                        AbstractUserRole.class)
                        .setParameter("clientOrganizationUuid", clientOrganizationUuid)
                        .setParameter("userUuid", userUuid)
                        .getSingleResult()
        );
    }

    @Override
    public Optional<AbstractUserRole> findClientContentManagerRoleByUserAndClientOrganization(final String userUuid, final String clientOrganizationUuid) {
        return find(() ->
                entityManager.createQuery(
                        "select aur from UserClientOrganizationContentManagerRole uoar join AbstractUserRole aur on aur.id = uoar.id " +
                                "where uoar.clientOrganization.uuid = :clientOrganizationUuid and uoar.user.uuid = :userUuid",
                        AbstractUserRole.class)
                        .setParameter("clientOrganizationUuid", clientOrganizationUuid)
                        .setParameter("userUuid", userUuid)
                        .getSingleResult()
        );
    }

    @Override
    public Optional<AbstractUserRole> findClientViewerRoleByUserAndClientOrganization(final String userUuid, final String clientOrganizationUuid) {
        return find(() ->
                entityManager.createQuery(
                        "select aur from UserClientOrganizationViewerRole uoar join AbstractUserRole aur on aur.id = uoar.id " +
                                "where uoar.clientOrganization.uuid = :clientOrganizationUuid and uoar.user.uuid = :userUuid",
                        AbstractUserRole.class)
                        .setParameter("clientOrganizationUuid", clientOrganizationUuid)
                        .setParameter("userUuid", userUuid)
                        .getSingleResult()
        );
    }
}
