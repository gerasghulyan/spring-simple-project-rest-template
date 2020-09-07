package com.vntana.core.helper.unit.comment.product

import com.vntana.core.domain.comment.ProductComment
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.comment.product.dto.ProductCommentCreateDto
import com.vntana.core.service.comment.product.dto.ProductCommentFindByProductUuidDto
import com.vntana.core.service.comment.product.dto.ProductCommentUpdateDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 16:55
 **/
open class ProductCommentCommonTestHelper : AbstractCommonTestHelper() {

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
            page: Int = 0,
            size: Int = 5,
            productUuid: String? = uuid()
    ): ProductCommentFindByProductUuidDto = ProductCommentFindByProductUuidDto(page, size, productUuid)

    fun buildProductCommentPage(
            productUuid: String? = uuid(),
            entities: List<ProductComment> = listOf(buildProductComment(productUuid = productUuid), buildProductComment(productUuid = productUuid)),
            total: Long = entities.size.toLong(),
            pageAble: Pageable = buildPageRequest(0, 5)
    ): Page<ProductComment> = PageImpl(entities, pageAble, total)

    fun buildProductComment(
            user: User = userCommonTestHelper.buildUser(),
            message: String? = uuid(),
            productUuid: String? = uuid()
    ): ProductComment = ProductComment(user, message, productUuid)

}