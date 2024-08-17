package com.enrique.actualizacionEstados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ActualizacionEstadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActualizacionEstadosApplication.class, args);
	}

}
