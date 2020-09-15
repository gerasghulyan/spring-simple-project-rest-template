package com.vntana.core.service.annotation;

import com.vntana.core.domain.annotation.Annotation;
import com.vntana.core.service.annotation.dto.AnnotationCreateDto;
import com.vntana.core.service.annotation.dto.AnnotationUpdateDto;
import com.vntana.core.service.annotation.dto.AnnotationFindByProductUuidDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 10:06
 */
public interface AnnotationService {

    Annotation create(final AnnotationCreateDto dto);

    Annotation update(final AnnotationUpdateDto dto);

    Annotation delete(final String uuid);

    Optional<Annotation> findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);

    Annotation getByUuid(final String uuid);
    
    Page<Annotation> findByProductUuid(final AnnotationFindByProductUuidDto dto);
}
