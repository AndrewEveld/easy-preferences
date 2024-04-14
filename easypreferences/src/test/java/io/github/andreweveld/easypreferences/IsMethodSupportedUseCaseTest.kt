package io.github.andreweveld.easypreferences

import io.github.andreweveld.easypreferences.IsMethodSupportedUseCase
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Method

class IsMethodSupportedUseCaseTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var useCase: IsMethodSupportedUseCase

    @MockK
    lateinit var method: Method

    @Before
    fun setup() {
        useCase = IsMethodSupportedUseCase()
    }

    @Test
    fun defaultMethodShouldBeSupported() {
        val expected = true
        every { method.isDefault } returns true

        val actual = useCase.execute(method)

        assertThat(actual, IsEqual(expected))
    }

    @Test
    fun methodLengthShouldBeLongerThanThreeCharacters() {
        val expected = false
        every { method.isDefault } returns false
        every { method.name } returns "noo"

        val actual = useCase.execute(method)

        assertThat(actual, IsEqual(expected))
    }

    @Test
    fun methodPrefixGetShouldBeSupported() {
        val expected = true
        every { method.isDefault } returns false
        every { method.name } returns "getPreference"

        val actual = useCase.execute(method)

        assertThat(actual, IsEqual(expected))
    }

    @Test
    fun methodPrefixSetShouldBeSupported() {
        val expected = true
        every { method.isDefault } returns false
        every { method.name } returns "setPreference"

        val actual = useCase.execute(method)

        assertThat(actual, IsEqual(expected))
    }

    @Test
    fun nonDefaultMethodWithNoGetOrSetPrefixShouldNotBeSupported() {
        val expected = false
        every { method.isDefault } returns false
        every { method.name } returns "thisShouldNotWork"

        val actual = useCase.execute(method)

        assertThat(actual, IsEqual(expected))
    }
}