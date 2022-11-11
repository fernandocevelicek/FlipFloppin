package com.grupo1.FlipFloppin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FlipFloppinApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlipFloppinApplication.class, args);
	}

}
