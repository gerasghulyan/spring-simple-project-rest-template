package com.vntana.core.rest.client.whitelist;

import com.vntana.core.model.whitelist.request.CreateOrUpdateWhitelistIpsRequest;
import com.vntana.core.model.whitelist.response.CreateOrUpdateWhitelistIpResponse;
import com.vntana.core.model.whitelist.response.GetWhitelistIpsByOrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 12:33 PM
 */
@FeignClient(name = "coreWhitelistIps", path = "whitelist-ips", url = "${ms.core.url}")
public interface WhitelistIpResourceClient {

    @PostMapping(path = "/create-or-update")
    CreateOrUpdateWhitelistIpResponse createOrUpdate(@RequestBody final CreateOrUpdateWhitelistIpsRequest request);

    @GetMapping(path = "/organizations/{uuid}")
    GetWhitelistIpsByOrganizationResponse getByOrganization(@PathVariable("uuid") final String organizationUuid);
}
