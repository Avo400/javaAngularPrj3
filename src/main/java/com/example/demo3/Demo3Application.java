package com.example.demo3;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Demo3Application {

	EntityManagerFactory entityManagerFactory = null;
	EntityManager entityManager = null;

	public static void main(String[] args) {
		SpringApplication.run(Demo3Application.class, args);
	}

	@Bean
	CommandLineRunner init(MonUser3Repository monUser3Repository) {
		return args -> {
			try {
				entityManagerFactory = Persistence.createEntityManagerFactory("javabdd");
				entityManager = entityManagerFactory.createEntityManager();

				List<MonUser3> monUser3s = entityManager.createQuery("from MonUser3", MonUser3.class).getResultList();
				for (MonUser3 monUser3 : monUser3s) {
					monUser3Repository.save(monUser3);

				}
				monUser3Repository.findAll().forEach(System.out::println);

			} catch (Exception e) {
				System.out.println(e);
			} finally {
				entityManager.close();
			}

		};
	}

}
