package com.vntana.core.rest.facade.comment.builder.impl;

import com.vntana.core.domain.comment.AbstractComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Set<String> find(final AbstractComment comment) {
        LOGGER.debug("Finding mentioned users uuids for the provided comment - {}", comment);
        Assert.notNull(comment, "The comment should not be null");
        final String message = comment.getMessage();
        final Pattern pattern = Pattern.compile("\\[~accountUuid:((.{8}-.{4}-.{4}-.{4}-.{12}))\\]");
        final Matcher matcher = pattern.matcher(message);
        final Set<String> mentionedUsersUuids = new HashSet<>();
        while (matcher.find()) {
            mentionedUsersUuids.add(matcher.group(1));
        }
        LOGGER.debug("Successfully found mentioned users uuids for the provided comment - {}, result - {}", comment, mentionedUsersUuids);
        return mentionedUsersUuids;
    }
}
