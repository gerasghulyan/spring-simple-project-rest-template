package com.vntana.core.persistence.annotation;

import com.vntana.core.domain.annotation.Annotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 10:25
 */
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {
    
    Optional<Annotation> findByUuid(final String uuid);
    
    boolean existsByUuid(final String uuid);
    
    Page<Annotation> findByProductUuidAndRemovedIsNullOrderByCreated(final String productUuid, final Pageable pageable);
}
