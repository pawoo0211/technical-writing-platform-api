package dev.techwrite.platform.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        @Value("\${techwrite.security.enabled:false}") securityEnabled: Boolean,
    ): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }
        http.cors(Customizer.withDefaults())
        http.authorizeHttpRequests { authorize ->
            authorize.requestMatchers("/actuator/health", "/actuator/info").permitAll()
            if (securityEnabled) {
                authorize.anyRequest().authenticated()
            } else {
                authorize.anyRequest().permitAll()
            }
        }

        if (securityEnabled) {
            http.oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(
        @Value("\${techwrite.security.allowed-origins:http://localhost:5173}") allowedOrigins: List<String>,
    ): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            this.allowedOrigins = allowedOrigins
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
