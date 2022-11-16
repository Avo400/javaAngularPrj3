package com.example.demo3;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonUser3Repository extends CrudRepository<MonUser3, Long> {
}