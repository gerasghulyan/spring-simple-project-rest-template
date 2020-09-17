package com.vntana.core.rest.facade.annotation.builder.impl

import com.vntana.core.helper.unit.annotation.AnnotationCommonTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.rest.facade.annotation.builder.AnnotationViewModelBuilder
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.builder.UserModelBuilder
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 14:40
 */
class AnnotationViewModelBuilderImplUnitTest : AbstractFacadeUnitTest() {

    private val commonTestHelper = AnnotationCommonTestHelper()
    private val userRestTestHelper = UserRestTestHelper()
    
    private lateinit var builder: AnnotationViewModelBuilder

    @Mock
    private lateinit var userModelBuilder: UserModelBuilder

    @Before
    fun prepare() {
        builder = AnnotationViewModelBuilderImpl(userModelBuilder)
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
        val annotation = commonTestHelper.buildAnnotation()
        val ownerViewModel = userRestTestHelper.buildUserViewResponseModel()
        resetAll()
        expect(userModelBuilder.build(annotation.user)).andReturn(ownerViewModel)
        replayAll()
        builder.build(annotation).let {
            assertThat(it.uuid).isEqualTo(annotation.uuid)
            assertThat(it.productUuid).isEqualTo(annotation.productUuid)
            assertThat(it.text).isEqualTo(annotation.text)
            assertThat(it.owner).isEqualTo(ownerViewModel)
            assertThat(it.number).isEqualTo(annotation.number)
            assertThat(it.d1).isEqualTo(annotation.d1)
            assertThat(it.d2).isEqualTo(annotation.d2)
            assertThat(it.d3).isEqualTo(annotation.d3)
        }
        verifyAll()
    }
}