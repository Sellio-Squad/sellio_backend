package org.shangahi.sellio_backend.security.config

import org.shangahi.sellio_backend.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { csrf ->
                csrf.disable()
            }.sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.authorizeHttpRequests {
                it.requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/v1/auth/**",
                    "/v1/forgot-password/**",
                    "/v1/category/all-categories",
                    "/v1/category/*",
                    "/v1/discounts/**",
                    "/v1/products/store/*",
                    "/v1/products/search",
                    "/v1/products/used",
                    "/v1/products/*",
                    "/v1/products/store/*/subcategory/*",
                    "/v1/product-items/trending",
                    "/v1/product-items/*/items",
                    "/v1/stores/store-details/*",
                    "/v1/stores/top-rating",
                    "/v1/stores/search",
                    "/v1/store-rating/**",
                    "v1/subcategories/store/*",
                    "/v1/subcategories/category/*",
                    "/v1/offers",
                    "/v1/thrift-products"
                ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

}