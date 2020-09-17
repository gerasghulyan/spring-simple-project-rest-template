package com.vntana.core.helper.comment

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.model.comment.request.CreateProductCommentRequestModel
import com.vntana.core.model.comment.request.DeleteProductCommentRequestModel
import com.vntana.core.model.comment.request.FindProductCommentByFilterRequestModel
import com.vntana.core.model.comment.request.UpdateProductCommentRequestModel
import com.vntana.core.model.comment.response.ProductCommentViewResponseModel
import com.vntana.core.model.user.response.get.model.UserViewResponseModel
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 4:29 PM
 */
@Component
class ProductCommentRestTestHelper : AbstractRestTestHelper() {

    private val userRestTestHelper = UserRestTestHelper()

    fun buildCreateProductCommentRequestModel(
            userUuid: String? = uuid(),
            productUuid: String? = uuid(),
            message: String? = uuid()
    ) = CreateProductCommentRequestModel(userUuid, productUuid, message)

    fun buildUpdateProductCommentRequestModel(
            uuid: String? = uuid(),
            userUuid: String? = uuid(),
            message: String? = uuid()
    ) = UpdateProductCommentRequestModel(uuid, userUuid, message)

    fun buildDeleteProductCommentRequestModel(
            uuid: String? = uuid(),
            userUuid: String? = uuid()
    ) = DeleteProductCommentRequestModel(uuid, userUuid)

    fun buildFindProductCommentByFilterRequestModel(
            page: Int = 0,
            size: Int = 10,
            productUuid: String? = uuid()
    ) = FindProductCommentByFilterRequestModel(page, size, productUuid)

    fun buildProductCommentViewResponseModel(uuid: String? = uuid(),
                                             productUuid: String? = uuid(),
                                             message: String? = uuid(),
                                             owner: UserViewResponseModel = userRestTestHelper.buildUserViewResponseModel(),
                                             taggedUsers: List<UserViewResponseModel> = listOf(userRestTestHelper.buildUserViewResponseModel()),
                                             created: LocalDateTime? = LocalDateTime.now(),
                                             updated: LocalDateTime? = LocalDateTime.now()
    ) = ProductCommentViewResponseModel(uuid, productUuid, message, owner, taggedUsers, created, updated)

    fun buildCommentWithTaggedUser(userUuid: String = uuid()): String = "[~accountUuid:$userUuid] ${uuid()}"
}