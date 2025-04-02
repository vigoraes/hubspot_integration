package vigoraes.hubspot_integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HubspotIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubspotIntegrationApplication.class, args);
	}

}
