package com.vntana.core.helper.unit.comment

import com.vntana.core.domain.comment.AbstractComment
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.comment.dto.CommentCreateDto

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 19:14
 */
open class CommentCommonTestHelper : AbstractCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()

    fun buildCommentCreateDto(
            userUuid: String? = uuid(),
            message: String? = uuid()
    ): CommentCreateDto = CommentCreateDto(userUuid, message)

    fun buildComment(
            user: User = userCommonTestHelper.buildUser(),
            message: String? = uuid()
    ): AbstractComment = AbstractComment(user, message)
}