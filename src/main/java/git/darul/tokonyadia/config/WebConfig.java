package git.darul.tokonyadia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins( "http://localhost:8082", "https://7b5f-182-253-247-224.ngrok-free.app")  // URL Ngrok dan Localhost
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Metode HTTP yang diizinkan
                .allowedHeaders("*")  // Header yang diizinkan
                .allowCredentials(true);  // Jika autentikasi diperlukan
    }
}
