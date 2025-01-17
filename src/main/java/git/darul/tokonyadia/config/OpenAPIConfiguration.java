package git.darul.tokonyadia.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Tokonyadia",
                version = "1.0",
                contact = @Contact(
                        name = "Tokonyadia",
                        url = "https://tokonyadia.com"
                )
        ),
        servers = {
                @io.swagger.v3.oas.annotations.servers.Server(
                        url = "https://7b5f-182-253-247-224.ngrok-free.app", // URL Ngrok HTTPS
                        description = "Ngrok HTTPS Server"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfiguration {
}
