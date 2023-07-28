package com.example.pfe_project;

import com.example.pfe_project.models.role.Role;
import com.example.pfe_project.repository.role.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LearningSpringBootAuthenticationApplication {

	@Autowired
	private RoleRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(LearningSpringBootAuthenticationApplication.class, args);
	}

	@PostConstruct
	public void initializeData() {
		repository.save(new Role("USER"));
		repository.save(new Role("ADMIN"));
	}
}
