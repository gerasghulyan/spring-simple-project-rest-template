package com.vntana.core.helper.unit.comment

import com.vntana.core.domain.comment.ProductComment
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.comment.product.dto.ProductCommentCreateDto

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 19:14
 */
open class CommentCommonTestHelper : AbstractCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()

    fun buildProductCommentCreateDto(
            userUuid: String? = uuid(),
            message: String? = uuid(),
            productUuid: String? = uuid()
    ): ProductCommentCreateDto = ProductCommentCreateDto(userUuid, productUuid, message)

    fun buildProductComment(
            user: User = userCommonTestHelper.buildUser(),
            message: String? = uuid(),
            productUuid: String? = uuid()
    ): ProductComment = ProductComment(user, message, productUuid)
}