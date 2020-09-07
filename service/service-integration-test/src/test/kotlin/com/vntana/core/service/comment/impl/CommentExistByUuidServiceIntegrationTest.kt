package com.vntana.core.service.comment.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 16:49
 **/
class CommentExistByUuidServiceIntegrationTest : AbstractCommentServiceIntegrationTest() {

    @Test
    fun `test when is not found`() {
        assertThat(integrationTestHelper.commentService.existsByUuid(uuid())).isFalse()
    }

    @Test
    fun `test when found`() {
        val comment = integrationTestHelper.persistComment()
        assertThat(integrationTestHelper.commentService.existsByUuid(comment.uuid)).isTrue()
    }
}