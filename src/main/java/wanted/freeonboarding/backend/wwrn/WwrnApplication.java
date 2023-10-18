package wanted.freeonboarding.backend.wwrn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableJpaAuditing
public class WwrnApplication {

	public static void main(String[] args) {
		SpringApplication.run(WwrnApplication.class, args);
	}

}
