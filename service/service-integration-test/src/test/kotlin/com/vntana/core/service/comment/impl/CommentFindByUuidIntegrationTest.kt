package com.vntana.core.service.comment.impl

import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 16:49
 **/
class CommentFindByUuidIntegrationTest : AbstractCommentServiceIntegrationTest() {

    @Test
    fun `test findByUuid`() {

        integrationTestHelper.persistComment().let { comment ->

            integrationTestHelper.commentService.findByUuid(comment.uuid).let {
                Assertions.assertThat(it.uuid).isEqualTo(comment.uuid)
            }
        }
    }

}