package net.engineeringdisgest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling // it will run the cron in every 10 mins
public class JournalApplication {

	public static void main(String[] args) {

		SpringApplication.run(JournalApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory databaseFactory) {
		return new MongoTransactionManager(databaseFactory);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


}

// Transactional commit or rollback is managed by PlatformTransactionManager
// In mongoDB there is MongoTransactionManager
