package git.darul.tokonyadia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TokonyadiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokonyadiaApplication.class, args);
	}

}
