package exchange.rate.v2.exchangev2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Exchangev2Application {

	public static void main(String[] args) {
		SpringApplication.run(Exchangev2Application.class, args);
	}

}