package com.vntana.core.service.common.component.impl;

import com.vntana.core.service.common.component.SlugValidationComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Geras Ghulyan
 * Date: 24.12.19
 * Time: 10:29
 */
@Component
public class SlugValidationComponentImpl implements SlugValidationComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlugValidationComponentImpl.class);
    private static final String URL_REGEX = "^[A-Za-z0-9._~-]+$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public SlugValidationComponentImpl() {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public boolean validate(final String url) {
        Assert.hasText(url, "The url should contain text");
        final Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }
}
