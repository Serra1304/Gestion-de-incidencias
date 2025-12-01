package com.incident_manager.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes permitidos (modifica según tu frontend)
        config.setAllowedOriginPatterns(List.of("*"/*"http://localhost:3000"*/));

        // Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Cabeceras permitidas
        config.setAllowedHeaders(
                List.of("*"
//                "Authorization",
//                "Content-Type",
//                "Accept"
        ));

        // Permitir credenciales si usas cookies o sesiones (JWT no lo requiere)
        config.setAllowCredentials(true);

        // Exponer headers al frontend
        config.setExposedHeaders(List.of("Authorization"));

        // Tiempo para cachear la política preflight
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}

