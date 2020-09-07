package com.vntana.core.helper.integration.comment

import com.vntana.core.domain.comment.AbstractComment
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.comment.CommentCommonTestHelper
import com.vntana.core.service.comment.CommentService
import com.vntana.core.service.comment.dto.CommentCreateDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 16:53
 **/
@Component
class CommentIntegrationTestHelper : CommentCommonTestHelper() {

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    lateinit var commentService: CommentService

    fun persistComment(
            userUuid: String? = userIntegrationTestHelper.persistUser().uuid,
            message: String? = uuid()
    ): AbstractComment {
        val dto: CommentCreateDto = buildCommentCreateDto(userUuid = userUuid, message = message)
        return commentService.create(dto)
    }
}