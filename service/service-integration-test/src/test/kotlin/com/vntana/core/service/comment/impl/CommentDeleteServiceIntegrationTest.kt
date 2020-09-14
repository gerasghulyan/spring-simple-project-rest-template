package com.vntana.core.service.comment.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 14:47
 */
class CommentDeleteServiceIntegrationTest : AbstractCommentServiceIntegrationTest() {

    @Test
    fun `delete test`() {
        val persistComment = integrationTestHelper.persistComment()
        commentService.delete(persistComment.uuid).let {
            assertThat(it.removed).isNotNull()
        }
    }
}