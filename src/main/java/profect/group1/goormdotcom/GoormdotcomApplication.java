package profect.group1.goormdotcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GoormdotcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoormdotcomApplication.class, args);
	}

}
