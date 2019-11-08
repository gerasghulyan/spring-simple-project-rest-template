package com.vntana.core.service.email.impl;

import com.vntana.core.service.email.EmailValidationComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class EmailValidationComponentImpl implements EmailValidationComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailValidationComponentImpl.class);

    public EmailValidationComponentImpl(){
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public boolean isValid(final String email) {
        Assert.hasText(email, "The email should not be empty");
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$");
    }
}
