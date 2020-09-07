package com.vntana.core.service.comment;

import com.vntana.core.domain.comment.AbstractComment;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 16:52
 */
public interface CommentService {

    AbstractComment findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);

    AbstractComment delete(final String uuid);
}
