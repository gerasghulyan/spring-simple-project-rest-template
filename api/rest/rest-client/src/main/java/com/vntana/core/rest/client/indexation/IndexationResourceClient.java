package com.vntana.core.rest.client.indexation;

import com.vntana.commons.api.model.response.indexation.IndexationResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/26/2020
 * Time: 12:19 PM
 */
@FeignClient(name = "coreClients", path = "indexations", url = "${ms.core.url}", primary = false)
public interface IndexationResourceClient {

    @GetMapping("/organizations")
    IndexationResultResponse indexAllOrganizations();
}