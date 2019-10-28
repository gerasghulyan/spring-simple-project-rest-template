package com.vntana.core.persistence.template.email;

import com.vntana.core.domain.template.email.TemplateEmail;
import com.vntana.core.domain.template.email.TemplateEmailType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/28/19
 * Time: 11:14 AM
 */
public interface TemplateEmailRepository extends JpaRepository<TemplateEmail, Long> {

    Optional<TemplateEmail> findByType(final TemplateEmailType type);
}
