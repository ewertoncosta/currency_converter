package com.currencyconverter.service

import com.currencyconverter.model.Users
import com.currencyconverter.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException

class CustomUserServiceTest {

    private val userRepository = mock(UserRepository::class.java)
    private val userService = CustomUserService(userRepository)

    @Test
    fun `test getAuthenticatedUsername`() {
        // Mock SecurityContext
        val authentication = mock(Authentication::class.java)
        val securityContext = mock(SecurityContext::class.java)
        `when`(securityContext.authentication).thenReturn(authentication)
        `when`(authentication.name).thenReturn("testUser")
        SecurityContextHolder.setContext(securityContext)

        val mockUser = Users().apply {
            username = "testUser"
            password = "testPassword"
        }

        `when`(userRepository.findByUsername("testUser")).thenReturn(mockUser)

        // Call the method
        val userDetails = userService.loadUserByUsername("testUser")

        // Assert
        assertEquals("testUser", userDetails.username)
    }

    @Test
    fun `test loadUserByUsername with non-existent user`() {
        // Mock behavior for non-existent user
        `when`(userRepository.findByUsername("nonExistentUser")).thenReturn(null)

        // Assert exception is thrown
        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userService.loadUserByUsername("nonExistentUser")
        }
        assertEquals("User not found", exception.message)
    }

    @Test
    fun `test loadUserByUsername with empty username`() {
        // Assert exception is thrown for empty username
        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userService.loadUserByUsername("")
        }
        assertEquals("User not found", exception.message)
    }

    @Test
    fun `test loadUserByUsername with missing SecurityContext`() {
        // Clear SecurityContext
        SecurityContextHolder.clearContext()

        // Assert exception is thrown
        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userService.loadUserByUsername("testUser")
        }
        assertEquals("User not found", exception.message)
    }
}