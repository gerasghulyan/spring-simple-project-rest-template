package com.vntana.core.service.comment.impl

import com.vntana.core.helper.integration.comment.CommentIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import com.vntana.core.service.comment.CommentService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Vardan Aivazian
 * Date: 07.09.2020
 * Time: 16:51
 **/
abstract class AbstractCommentServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var integrationTestHelper: CommentIntegrationTestHelper

    @Autowired
    protected lateinit var commentService: CommentService
}