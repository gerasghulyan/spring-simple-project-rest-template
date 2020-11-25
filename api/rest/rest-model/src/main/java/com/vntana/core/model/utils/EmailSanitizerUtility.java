package com.vntana.core.model.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/25/2020
 * Time: 5:25 PM
 */
public final class EmailSanitizerUtility {

    private EmailSanitizerUtility() {
        // Don't try to create an instance of this class
    }

    public static String sanitize(final String email) {
        if (StringUtils.isNotBlank(email)) {
            return email.trim().toLowerCase();
        }
        // Pass the empty/null value to request Validator
        return email;
    }
}