package com.vntana.core.service.template.email;

import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 11:12 AM
 */
public interface TemplateEmailService {

    TemplateEmail getByType(final TemplateEmailType type);
}
