package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 30.09.2020
 * Time: 11:15
 */
class UserGetByUuidsAndOrganizationUuidWebTest : AbstractUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                userResourceClient.getByUuidsAndOrganizationUuid(resourceHelper.buildGetByUuidsAndOrganizationUuid(uuids = null)),
                UserErrorResponseModel.MISSING_UUIDS
        )
        assertBasicErrorResultResponse(
                userResourceClient.getByUuidsAndOrganizationUuid(resourceHelper.buildGetByUuidsAndOrganizationUuid(uuids = emptySet())),
                UserErrorResponseModel.MISSING_UUIDS
        )
        assertBasicErrorResultResponse(
                userResourceClient.getByUuidsAndOrganizationUuid(resourceHelper.buildGetByUuidsAndOrganizationUuid(organizationUuid = null)),
                UserErrorResponseModel.MISSING_ORGANIZATION
        )
        assertBasicErrorResultResponse(
                userResourceClient.getByUuidsAndOrganizationUuid(resourceHelper.buildGetByUuidsAndOrganizationUuid(organizationUuid = emptyString())),
                UserErrorResponseModel.MISSING_ORGANIZATION
        )
        assertBasicErrorResultResponse(
                userResourceClient.getByUuidsAndOrganizationUuid(resourceHelper.buildGetByUuidsAndOrganizationUuid(organizationUuid = StringUtils.SPACE)),
                UserErrorResponseModel.MISSING_ORGANIZATION
        )
    }

    @Test
    fun `test when organization not found`() {
        val createUserRequest = resourceHelper.buildCreateUserRequest()
        val userUuid = resourceHelper.persistUser(createUserRequest).response().uuid
        val request = resourceHelper.buildGetByUuidsAndOrganizationUuid(uuids = setOf(userUuid))
        assertBasicErrorResultResponse(userResourceClient.getByUuidsAndOrganizationUuid(request),
                UserErrorResponseModel.NOT_FOUND_FOR_ORGANIZATION)
    }

    @Test
    fun `test when user not found`() {
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val request = resourceHelper.buildGetByUuidsAndOrganizationUuid(organizationUuid = organizationUuid)
        assertBasicErrorResultResponse(userResourceClient.getByUuidsAndOrganizationUuid(request),
                UserErrorResponseModel.NOT_FOUND_FOR_USERS_UUIDS)
    }
    
    @Test
    fun `test when user not in organization`() {
        val createUserRequest = resourceHelper.buildCreateUserRequest()
        val userUuid = resourceHelper.persistUser(createUserRequest).response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val request = resourceHelper.buildGetByUuidsAndOrganizationUuid(uuids = setOf(userUuid), organizationUuid = organizationUuid)
        userResourceClient.getByUuidsAndOrganizationUuid(request).let { responseEntity ->
            assertBasicSuccessResultResponse(responseEntity)
            responseEntity?.body?.response()?.let {
                assertThat(it.totalCount()).isEqualTo(1)
                assertThat(it.items()[0].fullName).isEqualTo(createUserRequest.fullName)
                assertThat(it.items()[0].email).isEqualTo(createUserRequest.email)
                assertThat(it.items()[0].isInOrganization).isFalse()
            }
        }
    }

    @Test
    fun test() {
        val createUserRequest = resourceHelper.buildCreateUserRequest()
        val userUuid = resourceHelper.persistUser(createUserRequest).response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization(userUuid = userUuid).response().uuid
        val request = resourceHelper.buildGetByUuidsAndOrganizationUuid(uuids = setOf(userUuid), organizationUuid = organizationUuid)
        userResourceClient.getByUuidsAndOrganizationUuid(request).let { responseEntity ->
            assertBasicSuccessResultResponse(responseEntity)
            responseEntity?.body?.response()?.let {
                assertThat(it.totalCount()).isEqualTo(1)
                assertThat(it.items()[0].fullName).isEqualTo(createUserRequest.fullName)
                assertThat(it.items()[0].email).isEqualTo(createUserRequest.email)
                assertThat(it.items()[0].isInOrganization).isTrue()
            }
        }
    }
}