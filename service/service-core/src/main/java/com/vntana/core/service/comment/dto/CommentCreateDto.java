package com.vntana.core.service.comment.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 17:10
 **/
public class CommentCreateDto implements ServiceDto {

    private final String userUuid;
    private final String message;

    public CommentCreateDto(final String userUuid, final String message) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.hasText(message, "The comment should not be null or empty");
        this.message = message;
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentCreateDto that = (CommentCreateDto) o;
        return Objects.equals(userUuid, that.userUuid) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userUuid, message);
    }

    @Override
    public String toString() {
        return "CommentCreateDto{" +
                "userUuid='" + userUuid + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getMessage() {
        return message;
    }
}