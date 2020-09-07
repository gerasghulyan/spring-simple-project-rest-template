package com.vntana.core.service.comment.impl

import com.vntana.core.helper.unit.comment.CommentCommonTestHelper
import com.vntana.core.persistence.comment.CommentRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.comment.CommentService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 15:57
 **/
abstract class AbstractCommentServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var commentService: CommentService
    protected val commonTestHelper = CommentCommonTestHelper()

    @Mock
    protected lateinit var commentRepository: CommentRepository

    @Before
    fun prepare() {
        commentService = CommentServiceImpl(commentRepository)
    }
}