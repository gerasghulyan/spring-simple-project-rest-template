package com.vntana.core.rest.facade.comment.builder.impl

import com.vntana.core.helper.unit.comment.CommentCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 9/17/20
 * Time: 2:33 PM
 */
class CommentTaggedUsersFinderServiceImplUnitTest : AbstractFacadeUnitTest() {

    private lateinit var commentTaggedUsersFinderService: CommentTaggedUsersFinderService

    private val commentCommonTestHelper = CommentCommonTestHelper()

    @Before
    fun prepare() {
        commentTaggedUsersFinderService = CommentTaggedUsersFinderServiceImpl()
    }

    @Test
    fun `test with illegal arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { commentTaggedUsersFinderService.find(null) }
        verifyAll()
    }

    @Test
    fun `test when nothing found`() {
        val comment = commentCommonTestHelper.buildProductComment(message = uuid())
        resetAll()
        replayAll()
        commentTaggedUsersFinderService.find(comment)
                .apply { assertThat(this).isEmpty() }
        verifyAll()
    }

    @Test
    fun `test when similar pattern was found`() {
        val uuid1 = uuid()
        val uuid2 = uuid()
        val message = "[accountUuid:$uuid1] ${uuid()} [~accountUui:$uuid2][~accountUuid:56-uu]"
        val comment = commentCommonTestHelper.buildProductComment(message = message)
        resetAll()
        replayAll()
        commentTaggedUsersFinderService.find(comment)
                .apply { assertThat(this).isEmpty() }
        verifyAll()
    }

    @Test
    fun test() {
        val uuid1 = uuid()
        val uuid2 = uuid()
        val message = "[~accountUuid:$uuid1] ${uuid()} [~accountUuid:$uuid2][~accountUuid:$uuid1]"
        val comment = commentCommonTestHelper.buildProductComment(message = message)
        resetAll()
        replayAll()
        commentTaggedUsersFinderService.find(comment)
                .apply { assertThat(this).containsExactlyInAnyOrder(uuid1, uuid2) }
        verifyAll()
    }
}