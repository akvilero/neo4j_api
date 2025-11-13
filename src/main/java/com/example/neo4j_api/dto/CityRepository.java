package com.example.neo4j_api.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends Neo4jRepository<City, String> {
    List<City> findByCountry(String country);
     
    Optional<City> findByName(String name);
    
}
