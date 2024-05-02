package samucabank.apibank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBankApplication.class, args);
	}

}
