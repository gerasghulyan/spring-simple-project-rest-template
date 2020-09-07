package com.vntana.core.service.comment.impl;

import com.vntana.commons.service.exception.EntityNotFoundForUuidException;
import com.vntana.core.domain.comment.AbstractComment;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.comment.CommentRepository;
import com.vntana.core.service.comment.CommentService;
import com.vntana.core.service.comment.dto.CommentCreateDto;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 12:45
 **/
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentServiceImpl(final CommentRepository commentRepository, final UserService userService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public AbstractComment create(final CommentCreateDto dto) {
        Assert.notNull(dto, "The CommentCreateDto should not be null");
        LOGGER.debug("Creating user {} comment", dto.getUserUuid());
        final User user = userService.getByUuid(dto.getUserUuid());
        AbstractComment abstractComment = new AbstractComment(user, dto.getMessage());
        AbstractComment savedAbstractComment = commentRepository.save(abstractComment);
        LOGGER.debug("Successfully creating user {} comment", dto.getUserUuid());
        return savedAbstractComment;
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
}
