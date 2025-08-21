package org.example.ngruppbe.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**") // Only match API endpoints
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() //authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/h2-console/**", "/login", "/login/**"))
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // Explicitly allow frontend
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/", configuration);
        source.registerCorsConfiguration("/**", configuration); // Applies to all endpoints
        source.registerCorsConfiguration("/login", configuration); // Explicitly for /login
        source.registerCorsConfiguration("/login/**", configuration); // Explicitly for /login/**
        return source;
    }

}
