package com.vntana.core.service.annotation.impl;

import com.vntana.commons.service.exception.EntityNotFoundForUuidException;
import com.vntana.core.domain.annotation.Annotation;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.annotation.AnnotationRepository;
import com.vntana.core.service.annotation.AnnotationService;
import com.vntana.core.service.annotation.dto.AnnotationCreateDto;
import com.vntana.core.service.annotation.dto.AnnotationFindByProductUuidDto;
import com.vntana.core.service.annotation.dto.AnnotationUpdateDto;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 10:24
 */
@Service
class AnnotationServiceImpl implements AnnotationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationServiceImpl.class);
    private static final String UUID_ASSERTION_TEXT = "The annotation uuid should not be null or empty";

    private final AnnotationRepository repository;
    private final UserService userService;

    public AnnotationServiceImpl(final AnnotationRepository repository, final UserService userService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.repository = repository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Annotation create(final AnnotationCreateDto dto) {
        Assert.notNull(dto, "The annotation creation dto should not be null");
        LOGGER.debug("Creating annotation for the provided dto - {}", dto);
        final User user = userService.getByUuid(dto.getUserUuid());
        final Annotation annotation = new Annotation(
                dto.getProductUuid(), user, dto.getText(), dto.getNumber(), false, dto.getD1(), dto.getD2(), dto.getD3()
        );
        final Annotation savedAnnotation = repository.save(annotation);
        LOGGER.debug("Successfully created annotation for the provided dto - {}", dto);
        return savedAnnotation;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Annotation> findByUuid(final String uuid) {
        Assert.hasText(uuid, UUID_ASSERTION_TEXT);
        LOGGER.debug("Retrieving annotation by uuid - {}", uuid);
        final Optional<Annotation> optionalAnnotation = repository.findByUuid(uuid);
        LOGGER.debug("Successfully retrieved annotation by uuid - {}", uuid);
        return optionalAnnotation;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUuid(final String uuid) {
        Assert.hasText(uuid, UUID_ASSERTION_TEXT);
        LOGGER.debug("Checking existence of annotation by uuid - {}", uuid);
        final boolean existence = repository.existsByUuid(uuid);
        LOGGER.debug("Successfully checked existence of annotation by uuid - {}", uuid);
        return existence;
    }

    @Transactional(readOnly = true)
    @Override
    public Annotation getByUuid(final String uuid) {
        LOGGER.debug("Get annotation by uuid - {}", uuid);
        final Annotation annotation = findByUuid(uuid).orElseThrow(() -> {
            LOGGER.error("Can not find annotation for uuid - {}", uuid);
            return new EntityNotFoundForUuidException(uuid, Annotation.class);
        });
        LOGGER.debug("Successfully got annotation by uuid - {}", uuid);
        return annotation;
    }
    
    @Transactional(readOnly = true)
    @Override
    public Page<Annotation> findByProductUuid(final AnnotationFindByProductUuidDto dto) {
        Assert.notNull(dto, "The annotation find by product uuid dto should not be null");
        LOGGER.debug("Retrieving annotation by the provided dto - {}", dto);
        final Page<Annotation> annotations = repository.findByProductUuidAndRemovedIsNullOrderByCreated(dto.getProductUuid(), PageRequest.of(dto.getPage(), dto.getSize()));
        LOGGER.debug("Successfully found annotation by the provided dto - {}", dto);
        return annotations;
    }

    @Transactional
    @Override
    public Annotation update(final AnnotationUpdateDto dto) {
        Assert.notNull(dto, "The annotation update dto should not be null");
        LOGGER.debug("Updating annotation for the provided dto - {}", dto);
        final Annotation annotation = getByUuid(dto.getUuid());
        annotation.setText(dto.getText());
        annotation.setResolved(dto.getResolved());
        annotation.setD1(dto.getD1());
        annotation.setD2(dto.getD2());
        annotation.setD3(dto.getD3());
        final Annotation savedAnnotation = repository.save(annotation);
        LOGGER.debug("Successfully updated annotation for the provided dto - {}", dto);
        return savedAnnotation;
    }

    @Transactional
    @Override
    public Annotation delete(final String uuid) {
        Assert.hasText(uuid, UUID_ASSERTION_TEXT);
        LOGGER.debug("Deleting annotation having uuid - {}", uuid);
        final Annotation annotation = findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundForUuidException(uuid, Annotation.class));
        annotation.setRemoved(LocalDateTime.now());
        final Annotation savedAnnotation = repository.save(annotation);
        LOGGER.debug("Successfully deleted annotation having uuid - {}", uuid);
        return savedAnnotation;
    }
}