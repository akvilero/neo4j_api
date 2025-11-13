package com.example.neo4j_api.dto;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends Neo4jRepository<Flight, String> {

}
