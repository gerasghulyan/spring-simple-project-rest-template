package com.vntana.core.helper.integration.comment

import com.vntana.core.domain.comment.AbstractComment
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.comment.CommentCommonTestHelper
import com.vntana.core.service.comment.product.ProductCommentService
import com.vntana.core.service.comment.product.dto.ProductCommentCreateDto
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
    private lateinit var productCommentService: ProductCommentService

    fun persistComment(
            userUuid: String? = userIntegrationTestHelper.persistUser().uuid,
            message: String? = uuid()
    ): AbstractComment {
        val dto: ProductCommentCreateDto = buildProductCommentCreateDto(userUuid = userUuid, message = message)
        return productCommentService.create(dto)
    }
}