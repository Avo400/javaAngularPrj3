package com.example.demo3;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MonUser3Controller {

	// standard constructors
	EntityManagerFactory entityManagerFactory = null;
	EntityManager entityManager = null;
	private final MonUser3Repository monUser3Repository;

	public MonUser3Controller(MonUser3Repository monUser3Repository) {
		this.monUser3Repository = monUser3Repository;
	}

	@GetMapping("/users")
	public List<MonUser3> getUsers() {

		return (List<MonUser3>) monUser3Repository.findAll();
	}

	@PostMapping("/users")
	void addUser(@RequestBody MonUser3 user) {
		try {

			insert(user);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@PutMapping("/users")
	void updateUser(@RequestBody MonUser3 user) {
		try {

			update(user);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@DeleteMapping("/users")
	void deleteUser(@RequestBody MonUser3 user) {
		try {

			delete(user);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void insert(MonUser3 monUser3) {

		System.out.println("- Ajout d'un user----------");

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("javabdd");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			EntityTransaction trans = entityManager.getTransaction();
			trans.begin();
			entityManager.persist(monUser3);
			trans.commit();
			monUser3Repository.save(monUser3);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (entityManager != null)
				entityManager.close();
			if (entityManagerFactory != null)
				entityManagerFactory.close();
		}
	}

	private void update(MonUser3 monUser3Upd) {
		System.out.println("- Modification d'un user ----------");
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("javabdd");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		MonUser3 monUser3 = monUser3Repository.findById(monUser3Upd.getId()).orElse(null);
		if (monUser3 != null) {

			MonUser3 realMonUser3 = monUser3;

			realMonUser3.setName(monUser3Upd.getName());
			realMonUser3.setEmail(monUser3Upd.getEmail());

			try {
				EntityTransaction trans = entityManager.getTransaction();
				trans.begin();

				entityManager.merge(realMonUser3);
				trans.commit();
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				if (entityManager != null)
					entityManager.close();
				if (entityManagerFactory != null)
					entityManagerFactory.close();
			}
			List<MonUser3> results = entityManager.createQuery("from MonUser3", MonUser3.class).getResultList();
			for (MonUser3 result : results) {
				System.out.println(result);
				monUser3Repository.save(result);
			}

		}

	}

	private void delete(MonUser3 monUser3Delete) {
		System.out.println("- Delete d'un user ----------");
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("javabdd");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		MonUser3 monUser3 = monUser3Repository.findById(monUser3Delete.getId()).orElse(null);
		if (monUser3 != null) {
			System.out.println("- Delete FOUND d'un user ----------");
			// MonUser2 realMonUser2 = monUser2;
			try {
				EntityTransaction trans = entityManager.getTransaction();
				trans.begin();
				entityManager.remove(entityManager.merge(monUser3));
				trans.commit();
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				if (entityManager != null)
					entityManager.close();
				if (entityManagerFactory != null)
					entityManagerFactory.close();
			}

		}

	}
}
