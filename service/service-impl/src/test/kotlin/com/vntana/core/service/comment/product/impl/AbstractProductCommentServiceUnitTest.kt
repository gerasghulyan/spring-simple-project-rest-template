package com.vntana.core.service.comment.product.impl

import com.vntana.core.helper.unit.comment.user.CommentCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.comment.user.product.ProductCommentRepository
import com.vntana.core.service.AbstractServiceUnitTest
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
    
    protected val commonTestHelper = CommentCommonTestHelper()
    protected val userCommonTestHelper = UserCommonTestHelper()

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var productCommentRepository: ProductCommentRepository


    @Before
    fun prepare() {
        productCommentService = ProductCommentServiceImpl(productCommentRepository, userService)
    }
    
}