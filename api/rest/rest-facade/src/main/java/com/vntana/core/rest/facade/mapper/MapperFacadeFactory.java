package com.vntana.core.rest.facade.mapper;

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
    public void configure(final MapperFactory orikaMapperFactory) {

    }
}
