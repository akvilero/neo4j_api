package com.example.neo4j_api.dto;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends Neo4jRepository<Airport, String> {
    
    //@Query("MATCH (a:Airport)-[:LOCATED_IN]->(c:City) WHERE c.name = $cityName RETURN a")
    //List<Airport> findAirportsByCityName(@Param("cityName") String cityName);
    
    List<Airport> findByCityName(@Param("cityName") String cityName);
    
}