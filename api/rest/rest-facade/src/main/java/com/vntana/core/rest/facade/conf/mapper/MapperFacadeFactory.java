package com.vntana.core.rest.facade.conf.mapper;

import com.vntana.core.domain.whitelist.WhitelistIp;
import com.vntana.core.model.whitelist.response.model.GetWhitelistIpResponseModel;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:30 PM
 */
@Component
public class MapperFacadeFactory implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(final MapperFactory mapperFactory) {
        initWhitelistIpMappings(mapperFactory);
    }

    private void initWhitelistIpMappings(final MapperFactory mapperFactory) {
        mapperFactory.classMap(WhitelistIp.class, GetWhitelistIpResponseModel.class)
                .field("organization.uuid", "organizationUuid")
                .byDefault()
                .register();
    }
}
