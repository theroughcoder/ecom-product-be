package be.ecom_backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.key}")
    private String jwtKey;

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtKey);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(new AntPathRequestMatcher("/**", "OPTIONS")).permitAll();
                    auth.requestMatchers(
                        new AntPathRequestMatcher("/api/products/**"),
                        new AntPathRequestMatcher("/api/v1/s3/upload-image"),
                        new AntPathRequestMatcher("/api/search")
                    ).permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(withDefaults());
                })
                .build();
    }
}
