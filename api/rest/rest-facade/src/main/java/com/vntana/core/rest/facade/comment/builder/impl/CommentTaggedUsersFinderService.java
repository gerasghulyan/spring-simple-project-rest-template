package com.vntana.core.rest.facade.comment.builder.impl;

import com.vntana.core.domain.comment.AbstractComment;

import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 4:05 PM
 */
interface CommentTaggedUsersFinderService {

    List<String> find(final AbstractComment comment);
}
