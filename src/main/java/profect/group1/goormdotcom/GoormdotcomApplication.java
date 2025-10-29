package profect.group1.goormdotcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableAsync //비동기 이벤트 처리를 위한 어노테이션
@SpringBootApplication
@EnableFeignClients
public class GoormdotcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoormdotcomApplication.class, args);
	}

}
