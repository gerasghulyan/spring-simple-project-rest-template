package com.vntana.core.rest.resource.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 2:25 PM
 */
@RestController
@RequestMapping(path = "/", produces = "text/plain")
public class HealthCheckResource {

    @GetMapping(path = "/health")
    public String health() {
        return "OK";
    }

}
