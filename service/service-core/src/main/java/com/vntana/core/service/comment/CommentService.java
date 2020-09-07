package com.vntana.core.service.comment;

import com.vntana.core.domain.comment.AbstractComment;
import com.vntana.core.service.comment.dto.CommentCreateDto;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 16:52
 */
public interface CommentService {
    
    AbstractComment create(final CommentCreateDto dto);
}
