package com.vntana.core.service.comment.impl;

import com.vntana.commons.service.exception.EntityNotFoundForUuidException;
import com.vntana.core.domain.comment.AbstractComment;
import com.vntana.core.persistence.comment.CommentRepository;
import com.vntana.core.service.comment.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 12:45
 **/
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;

    public CommentServiceImpl(final CommentRepository commentRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public AbstractComment findByUuid(final String uuid) {
        Assert.hasText(uuid, "The comment uuid should not be null or empty");
        LOGGER.debug("Retrieving user comments by uuid - {}", uuid);
        final AbstractComment comment = commentRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundForUuidException(uuid, AbstractComment.class));
        LOGGER.debug("Successfully retrieved user comments by uuid - {}", uuid);
        return comment;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUuid(final String uuid) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        LOGGER.debug("Checking existence of user comment by uuid - {}", uuid);
        final boolean existence = commentRepository.existsByUuid(uuid);
        LOGGER.debug("Successfully checked existence of user comment by uuid - {}", uuid);
        return existence;
    }

    @Transactional
    @Override
    public AbstractComment delete(final String uuid) {
        Assert.hasText(uuid, "The comment uuid should not be null or empty");
        LOGGER.debug("Deleting comment having uuid - {}", uuid);
        AbstractComment abstractComment = findByUuid(uuid);
        abstractComment.setRemoved(LocalDateTime.now());
        AbstractComment deletedComment = commentRepository.save(abstractComment);
        LOGGER.debug("Successfully deleted comment having uuid - {}", uuid);
        return deletedComment;
    }
}
