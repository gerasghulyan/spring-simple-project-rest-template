package com.vntana.core.rest.facade.comment.builder.impl

import com.vntana.core.helper.unit.comment.product.ProductCommentCommonTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.rest.facade.comment.builder.ProductCommentViewModelBuilder
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.builder.UserModelBuilder
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:44 PM
 */
class ProductCommentViewModelBuilderImplUnitTest : AbstractFacadeUnitTest() {

    private lateinit var builder: ProductCommentViewModelBuilder

    @Mock
    private lateinit var commentTaggedUsersFinderService: CommentTaggedUsersFinderService

    @Mock
    private lateinit var userModelBuilder: UserModelBuilder

    private val commonTestHelper = ProductCommentCommonTestHelper()
    private val userRestTestHelper = UserRestTestHelper()

    @Before
    fun prepare() {
        builder = ProductCommentViewModelBuilderImpl(commentTaggedUsersFinderService, userModelBuilder)
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { builder.build(null) }
        verifyAll()
    }

    @Test
    fun test() {
        val comment = commonTestHelper.buildProductComment()
        val taggedUserUuids = listOf(uuid(), uuid())
        val ownerViewModel = userRestTestHelper.buildUserViewResponseModel()
        val taggedUser1ViewModel = userRestTestHelper.buildUserViewResponseModel()
        val taggedUser2ViewModel = userRestTestHelper.buildUserViewResponseModel()
        resetAll()
        expect(commentTaggedUsersFinderService.find(comment)).andReturn(taggedUserUuids)
        expect(userModelBuilder.build(comment.user)).andReturn(ownerViewModel)
        expect(userModelBuilder.build(taggedUserUuids[0])).andReturn(taggedUser1ViewModel)
        expect(userModelBuilder.build(taggedUserUuids[1])).andReturn(taggedUser2ViewModel)
        replayAll()
        builder.build(comment)
                .apply { assertThat(this.uuid).isEqualTo(comment.uuid) }
                .apply { assertThat(this.productUuid).isEqualTo(comment.productUuid) }
                .apply { assertThat(this.message).isEqualTo(comment.message) }
                .apply { assertThat(this.owner).isEqualTo(ownerViewModel) }
                .apply { assertThat(this.taggedUsers).containsExactly(taggedUser1ViewModel, taggedUser2ViewModel) }
        verifyAll()
    }
}