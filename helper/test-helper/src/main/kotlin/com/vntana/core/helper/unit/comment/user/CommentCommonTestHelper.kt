package com.vntana.core.helper.unit.comment.user

import com.vntana.core.domain.comment.ProductComment
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.comment.product.dto.ProductCommentCreateDto
import com.vntana.core.service.comment.product.dto.ProductCommentFindByProductUuidDto
import com.vntana.core.service.comment.product.dto.ProductCommentUpdateDto

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 19:14
 */
open class CommentCommonTestHelper : AbstractCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()
    
    fun buildProductCommentCreateDto(
            userUuid: String? = uuid(),
            productUuid: String? = uuid(),
            message: String? = uuid()
    ): ProductCommentCreateDto = ProductCommentCreateDto(userUuid, productUuid, message)
    
    fun buildProductCommentUpdateDto(
            uuid: String? = uuid(),
            message: String? = uuid()
    ): ProductCommentUpdateDto = ProductCommentUpdateDto(uuid, message)
    
    fun buildProductCommentFindByProductUuidDto(
            productUuid: String? = uuid()
    ): ProductCommentFindByProductUuidDto = ProductCommentFindByProductUuidDto(productUuid)
    
    fun buildProductComment(
            user: User = userCommonTestHelper.buildUser(),
            message: String? = uuid(),
            productUuid: String? = uuid()
    ): ProductComment = ProductComment(user, message, productUuid)
    
    fun buildProductCommentWithUuid(
            uuid: String? = uuid(),
            user: User = userCommonTestHelper.buildUser(),
            message: String? = uuid(),
            productUuid: String? = uuid()
    ): ProductComment = ProductComment(uuid, user, message, productUuid)
}