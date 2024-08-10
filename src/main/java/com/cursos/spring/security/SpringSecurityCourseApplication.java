package com.cursos.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

// @RequiredArgsConstructor
@SpringBootApplication
public class SpringSecurityCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityCourseApplication.class, args);
	}

	/* private final PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner createPasswordsCommand() {
		return args -> {
			System.out.println( passwordEncoder.encode( "clave123" ) );
			System.out.println( passwordEncoder.encode( "clave456" ) );
		};
	} */

}
