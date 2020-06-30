package com.vntana.core.rest.client.indexation;

import com.vntana.commons.api.model.response.indexation.IndexationResultResponse;
import com.vntana.core.model.indexation.OrganizationIndexationByUuidResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/26/2020
 * Time: 12:19 PM
 */
@FeignClient(name = "coreIndexationResourceClients", path = "indexations", url = "${ms.core.url}", primary = false)
public interface IndexationResourceClient {

    @GetMapping("/organizations")
    ResponseEntity<IndexationResultResponse> indexAllOrganizations();

    @GetMapping("/organization-invitations")
    ResponseEntity<IndexationResultResponse> indexAllOrganizationInvitations();

    @GetMapping("/organizations/{uuid}")
    ResponseEntity<OrganizationIndexationByUuidResultResponse> reindexByUuid(@PathVariable("uuid") final String uuid);
}