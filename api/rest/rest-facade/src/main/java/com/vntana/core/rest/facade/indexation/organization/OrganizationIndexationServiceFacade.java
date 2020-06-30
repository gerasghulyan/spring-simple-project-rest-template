package com.vntana.core.rest.facade.indexation.organization;


import com.vntana.commons.indexation.service.IndexationServiceFacade;
import com.vntana.core.model.indexation.OrganizationIndexationByUuidResultResponse;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/24/2020
 * Time: 5:14 PM
 */
public interface OrganizationIndexationServiceFacade extends IndexationServiceFacade {

    OrganizationIndexationByUuidResultResponse reindexByUuid(final String uuid);

}