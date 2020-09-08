package com.vntana.core.rest.facade.comment.builder.impl;

import com.vntana.core.domain.comment.AbstractComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 4:05 PM
 */
@Component
class CommentTaggedUsersFinderServiceImpl implements CommentTaggedUsersFinderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentTaggedUsersFinderServiceImpl.class);

    public CommentTaggedUsersFinderServiceImpl() {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public List<String> find(final AbstractComment comment) {
        return Collections.emptyList();
    }
}
