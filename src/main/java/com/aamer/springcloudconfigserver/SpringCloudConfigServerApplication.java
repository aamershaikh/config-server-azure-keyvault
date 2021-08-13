package com.aamer.springcloudconfigserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
/*
	to access the spring cloud config url : use  "http://localhost:8080/<filename>/<profile>" i.e http://localhost:8080/application/default
 */
@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServerApplication implements CommandLineRunner {

	@Value("${connectionString}")
	private String connectionString;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("connection string is "+connectionString);
	}
}