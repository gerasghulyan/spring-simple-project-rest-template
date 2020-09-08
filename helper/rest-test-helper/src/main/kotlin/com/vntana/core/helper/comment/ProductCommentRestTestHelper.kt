package com.vntana.core.helper.comment

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.model.comment.request.CreateProductCommentRequestModel
import com.vntana.core.model.comment.request.FindProductCommentByFilterRequestModel
import com.vntana.core.model.comment.response.ProductCommentViewResponseModel
import com.vntana.core.model.user.response.get.model.UserViewResponseModel
import org.springframework.stereotype.Component

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

    fun buildFindProductCommentByFilterRequestModel(
            page: Int = 0,
            size: Int = 10,
            productUuid: String? = uuid()
    ) = FindProductCommentByFilterRequestModel(page, size, productUuid)

    fun buildProductCommentViewResponseModel(uuid: String? = uuid(),
                                             productUuid: String? = uuid(),
                                             message: String? = uuid(),
                                             owner: UserViewResponseModel = userRestTestHelper.buildUserViewResponseModel(),
                                             taggedUsers: List<UserViewResponseModel> = listOf(userRestTestHelper.buildUserViewResponseModel())
    ) = ProductCommentViewResponseModel(uuid, productUuid, message, owner, taggedUsers)
}