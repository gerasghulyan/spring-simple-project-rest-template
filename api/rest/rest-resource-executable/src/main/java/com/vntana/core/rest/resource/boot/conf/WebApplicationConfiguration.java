package com.vntana.core.rest.resource.boot.conf;

import com.vntana.cache.conf.VntanaCacheConf;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 5:23 PM
 */
@Import(VntanaCacheConf.class)
@Configuration
@EnableFeignClients({"com.vntana.core.rest.client", "com.vntana.payment.client"})
@ComponentScan({"com.vntana.core"})
@EnableJpaRepositories("com.vntana.core.persistence")
@EntityScan("com.vntana.core.domain")
@PropertySource("classpath:application.properties")
public class WebApplicationConfiguration {
}
