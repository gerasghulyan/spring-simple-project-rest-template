package com.vntana.core.rest.resource.indexation;

import com.vntana.commons.api.model.response.indexation.IndexationResultResponse;
import com.vntana.core.rest.facade.indexation.organization.OrganizationIndexationServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 11:37 AM
 */
@RestController
@RequestMapping(path = "/indexations")
public class IndexationResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexationResource.class);

    private final OrganizationIndexationServiceFacade organizationIndexationServiceFacade;

    public IndexationResource(final OrganizationIndexationServiceFacade organizationIndexationServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationIndexationServiceFacade = organizationIndexationServiceFacade;
    }

    @GetMapping("/organizations")
    public ResponseEntity<IndexationResultResponse> indexAllOrganizations() {
        LOGGER.debug("Processing all organizations re-indexation");
        return ResponseEntity.ok(organizationIndexationServiceFacade.reindexAll());
    }
}