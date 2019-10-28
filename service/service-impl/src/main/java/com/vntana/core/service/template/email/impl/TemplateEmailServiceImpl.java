package com.vntana.core.service.template.email.impl;

import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import com.vntana.core.persistence.template.email.TemplateEmailRepository;
import com.vntana.core.service.template.email.TemplateEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 11:14 AM
 */
@Service
public class TemplateEmailServiceImpl implements TemplateEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateEmailServiceImpl.class);

    private final TemplateEmailRepository templateEmailRepository;

    public TemplateEmailServiceImpl(final TemplateEmailRepository templateEmailRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.templateEmailRepository = templateEmailRepository;
    }

    @Override
    public TemplateEmail getByType(final TemplateEmailType type) {
        LOGGER.debug("Retrieving TemplateEmail by TemplateEmailType - {}", type);
        Assert.notNull(type, "The TemplateEmailType should not be null");
        final TemplateEmail templateEmail = templateEmailRepository.findByType(type).orElseThrow(() ->
            new EntityNotFoundException(String.format("Cold not found %s for type %s", TemplateEmail.class.getCanonicalName(), type))
        );
        LOGGER.debug("Successfully retrieved TemplateEmail for type - {}", type);
        return templateEmail;
    }
}
