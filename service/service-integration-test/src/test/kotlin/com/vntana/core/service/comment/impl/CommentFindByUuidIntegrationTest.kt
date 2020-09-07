package com.vntana.core.service.comment.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 16:49
 **/
class CommentFindByUuidIntegrationTest : AbstractCommentServiceIntegrationTest() {

    @Test
    fun `test findByUuid`() {
        val persistComment = integrationTestHelper.persistComment()
        flushAndClear()
        commentService.findByUuid(persistComment.uuid).let {
            assertThat(it).isEqualTo(persistComment)
        }
    }
}