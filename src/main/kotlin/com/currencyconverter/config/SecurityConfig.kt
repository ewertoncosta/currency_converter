package com.currencyconverter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import com.currencyconverter.service.CustomUserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customUserService: CustomUserService
) {
    // 1) Tell Spring Security to ignore swagger completely
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers(
                "/v3/api-docs",       // the JSON root
                "/v3/api-docs/**",    // sub-paths (swagger-config, group defs…)
                "/swagger-ui.html",   // the UI entry-point
                "/swagger-ui/**",     // all swagger-ui assets (CSS, JS)
                "/webjars/**"         // springdoc’s webjars
            )
        }
    }

    // 2) Secure everything else
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.anyRequest().authenticated()
            }
            .httpBasic { }  // still protect your API with basic auth
            .userDetailsService(customUserService)

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}