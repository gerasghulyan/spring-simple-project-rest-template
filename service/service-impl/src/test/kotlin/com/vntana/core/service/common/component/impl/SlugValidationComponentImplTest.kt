package com.vntana.core.service.common.component.impl

import com.vntana.core.service.common.AbstractSlugValidationComponentTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 24.12.19
 * Time: 10:51
 */
class SlugValidationComponentImplTest : AbstractSlugValidationComponentTest() {

    @Test
    fun `test validate`() {
        assertThat(slugValidationComponent.validate("slug")).isTrue()
        assertThat(slugValidationComponent.validate("slug-slug")).isTrue()
        assertThat(slugValidationComponent.validate("slug-slug3")).isTrue()
        assertThat(slugValidationComponent.validate("slug-slug~3")).isTrue()
        assertThat(slugValidationComponent.validate("#slug")).isFalse()
        assertThat(slugValidationComponent.validate("slug 2")).isFalse()
    }

}