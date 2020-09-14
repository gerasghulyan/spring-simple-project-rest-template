package com.vntana.core.helper.integration.comment.product

import com.vntana.core.domain.comment.ProductComment
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.comment.product.ProductCommentCommonTestHelper
import com.vntana.core.service.comment.product.ProductCommentService
import com.vntana.core.service.comment.product.dto.ProductCommentCreateDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 14:17
 */
@Component
class ProductCommentIntegrationTestHelper : ProductCommentCommonTestHelper() {

    @Autowired
    private lateinit var productCommentService: ProductCommentService
    
    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    fun persistProductComment(
            userUuid: String? = userIntegrationTestHelper.persistUser().uuid,
            message: String? = uuid(),
            productUuid: String? = uuid() 
    ): ProductComment {
        val dto: ProductCommentCreateDto = buildProductCommentCreateDto(userUuid = userUuid, message = message, productUuid = productUuid)
        return productCommentService.create(dto)
    }
    
}