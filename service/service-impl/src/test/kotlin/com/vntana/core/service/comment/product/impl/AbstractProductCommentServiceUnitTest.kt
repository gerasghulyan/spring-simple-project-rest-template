package com.vntana.core.service.comment.product.impl

import com.vntana.core.helper.unit.comment.product.ProductCommentCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.comment.product.ProductCommentRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.comment.CommentService
import com.vntana.core.service.comment.product.ProductCommentService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 18:14
 */
abstract class AbstractProductCommentServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var productCommentService: ProductCommentService

    protected val commonTestHelper = ProductCommentCommonTestHelper()
    protected val userCommonTestHelper = UserCommonTestHelper()

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var productCommentRepository: ProductCommentRepository

    @Mock
    protected lateinit var commentService: CommentService

    @Before
    fun prepare() {
        productCommentService = ProductCommentServiceImpl(productCommentRepository, userService, commentService)
    }
}