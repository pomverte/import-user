package fr.pomverte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:spring/*")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ImportUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImportUserApplication.class, args);
	}
}
