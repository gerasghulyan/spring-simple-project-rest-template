package com.vntana.core.service.catalog

import com.vntana.core.helper.unit.catalog.FacebookCatalogSettingCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.persistence.catalog.FacebookCatalogSettingRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.catalog.impl.FacebookCatalogSettingServiceImpl
import com.vntana.core.service.organization.OrganizationService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 11:06 AM
 */
abstract class AbstractFacebookCatalogSettingServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var facebookCatalogSettingService: FacebookCatalogSettingService

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var facebookCatalogSettingRepository: FacebookCatalogSettingRepository

    protected val helper: FacebookCatalogSettingCommonTestHelper = FacebookCatalogSettingCommonTestHelper()

    protected val organizationTestHelper = OrganizationCommonTestHelper()

    @Before
    fun before() {
        facebookCatalogSettingService = FacebookCatalogSettingServiceImpl(
            facebookCatalogSettingRepository
        )
    }
}