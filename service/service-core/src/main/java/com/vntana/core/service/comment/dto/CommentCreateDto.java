package com.vntana.core.service.comment.dto;

import com.vntana.commons.service.dto.ServiceDto;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 16:53
 */
public class CommentCreateDto implements ServiceDto {

    private final String userUuid;

    private final String message;

    public CommentCreateDto(final String userUuid, final String message) {
        this.userUuid = userUuid;
        this.message = message;
    }
}
