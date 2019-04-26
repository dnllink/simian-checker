package br.com.bonaldo.simianchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class SimianCheckerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SimianCheckerApplication.class, args);
	}
}