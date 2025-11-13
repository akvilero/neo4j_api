package com.example.neo4j_api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.StatementBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.neo4j_api.dto.Airport;
import com.example.neo4j_api.dto.AirportInfoReturn;
import com.example.neo4j_api.dto.AirportRepository;
import com.example.neo4j_api.dto.City;
import com.example.neo4j_api.dto.CityRepository;
import com.example.neo4j_api.dto.Flight;

import com.example.neo4j_api.dto.FlightInfoResponse;
import com.example.neo4j_api.dto.FlightSearchResponse;

import com.example.neo4j_api.dto.FlightRepository;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import java.util.*;

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.StatementBuilder;

import java.util.stream.Collectors;




@RestController
public class Controller {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;
     @Autowired
    private Neo4jClient neo4jClient;
   


    @PutMapping("/cities")
    public ResponseEntity<?> registerNewCity(@RequestBody City city) {
        if (city.getName() == null || city.getName().isEmpty() || city.getCountry() == null || city.getCountry().isEmpty()) {
            return new ResponseEntity<>("Name and country are required.", HttpStatus.BAD_REQUEST);
        }

        City savedCity = cityRepository.save(city);
        return new ResponseEntity<>(savedCity, HttpStatus.NO_CONTENT);  // dokumentacijoj 201 o testuose 204 reikia
    }

    @GetMapping("/cities")
    public ResponseEntity<?> getCities(@RequestParam(value = "country", required = false) String country) {
        if (country != null && !country.isEmpty()) {
            return new ResponseEntity<>(cityRepository.findByCountry(country), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
        }
    }


    @GetMapping("/cities/{name}")
    public ResponseEntity<?> getCity(@PathVariable String name) {
        Optional<City> city = cityRepository.findById(name);
        if (city.isEmpty()) {
            return new ResponseEntity<>("City not found.", HttpStatus.NOT_FOUND);            
        }
        return new ResponseEntity<>(city.get(), HttpStatus.OK);
    }



    @PutMapping("/cities/{name}/airports")
    public ResponseEntity<?> registerNewAirport(@PathVariable String name, @RequestBody AirportInfoReturn airporInfoReturn) {
        Optional<City> optionalCity = cityRepository.findByName(airporInfoReturn.getCity());

        if (optionalCity.isEmpty()) {
            return new ResponseEntity<>("City not found.", HttpStatus.NOT_FOUND);  
        }
       
        City city = optionalCity.get();

        Airport airport = new Airport(
            airporInfoReturn.getCode(),
            airporInfoReturn.getName(),
            airporInfoReturn.getNumberOfTerminals(),
            airporInfoReturn.getAddress(),
            city
        );
        
        airportRepository.save(airport);
        return new ResponseEntity<>(airport, HttpStatus.NO_CONTENT);   
    }

    @GetMapping("/cities/{name}/airports")
    public ResponseEntity<?> getAirportsByCity(@PathVariable String name) {
       
        List<Airport> airports = airportRepository.findByCityName(name);
        if (airports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No airports found for the given city.");
        }
       
        List<AirportInfoReturn> airporInfoReturn = airports.stream()
        .map(airport -> new AirportInfoReturn(
            airport.getCode(),
            airport.getName(),
            airport.getCity() != null ? airport.getCity().getName() : null,
            airport.getNumberOfTerminals(),
            airport.getAddress()
        ))
        .collect(Collectors.toList());

        return new ResponseEntity<>(airporInfoReturn, HttpStatus.OK);
    }



    @GetMapping("/airports/{code}")
    public ResponseEntity<?> getAirportByCode(@PathVariable String code) {
        Optional<Airport> optionalAirport = airportRepository.findById(code);
        if (optionalAirport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Airport not found.");
        }
        Airport airport = optionalAirport.get();
        AirportInfoReturn airporInfoReturn = new AirportInfoReturn(
            airport.getCode(),
            airport.getName(),
            airport.getCity() != null ? airport.getCity().getName() : null,
            airport.getNumberOfTerminals(),
            airport.getAddress()
        );

        return new ResponseEntity<>(airporInfoReturn, HttpStatus.OK);    
    }


    @PutMapping("/flights")
    public ResponseEntity<?> registerNewFlight(@RequestBody Map<String, Object> flightDetails) {
        String number = (String) flightDetails.get("number");
        String fromAirportCode = (String) flightDetails.get("fromAirport");
        String toAirportCode = (String) flightDetails.get("toAirport");
        Integer flightTimeInMinutes = (Integer) flightDetails.get("flightTimeInMinutes");
        String operator = (String) flightDetails.get("operator");

        if (number == null || fromAirportCode == null || toAirportCode == null) {
            return new ResponseEntity<>("Flight number, fromAirport, and toAirport are required.", HttpStatus.BAD_REQUEST);
        }

        Double price;
        Object priceObj = flightDetails.get("price");

        if (priceObj instanceof Integer) {
            price = ((Integer) priceObj).doubleValue();
        } else if (priceObj instanceof Double) {
            price = (Double) priceObj;
        } else {
            return ResponseEntity.badRequest().body("Invalid price format.");
        }

        Optional<Airport> fromAirport = airportRepository.findById(fromAirportCode);
        Optional<Airport> toAirport = airportRepository.findById(toAirportCode);

        if (fromAirport.isEmpty() || toAirport.isEmpty()) {
            return new ResponseEntity<>("Invalid airport codes provided.", HttpStatus.BAD_REQUEST);
        }

        Flight flight = new Flight(
            number,
            price,
            flightTimeInMinutes,
            operator,
            fromAirport.get(),
            toAirport.get()
        );

        flightRepository.save(flight);

        return new ResponseEntity<>(flight, HttpStatus.NO_CONTENT);
    }


    @GetMapping("/flights/{number}")
    public ResponseEntity<?> getFlight(@PathVariable String number) {
        Optional<Flight> flightOptional = flightRepository.findById(number);

        if (flightOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found.");
        }

        Flight flight = flightOptional.get();

        String fromCity = flight.getFromAirport().getCity().getName();
        String toCity = flight.getToAirport().getCity().getName();

        FlightInfoResponse flightInfoResponse = new FlightInfoResponse(
            flight.getNumber(),
            flight.getFromAirport().getCode(),
            flight.getToAirport().getCode(),
            flight.getPrice(),
            flight.getFlightTimeInMinutes(),
            flight.getOperator(),
            fromCity,
            toCity
        );

        return new ResponseEntity<>(flightInfoResponse, HttpStatus.OK);

    }

    public List<FlightSearchResponse> findFlightsBetweenCities(String fromCity, String toCity) {
        String query = 
        "MATCH (from:Airport)-[:LOCATED_IN]->(fromCity:City),\n" +
        "      (to:Airport)-[:LOCATED_IN]->(toCity:City),\n" +
        "      (f:Flight)-[:DEPARTS_FROM]->(from),\n" +
        "      (f)-[:ARRIVES_AT]->(to)\n" +
        "WHERE fromCity.name = $fromCity AND toCity.name = $toCity\n" +
        "RETURN \n" +
        "    from.code AS fromAirport, \n" +
        "    to.code AS toAirport, \n" +
        "    COLLECT(f.number) AS flights, \n" +
        "    f.price AS totalPrice, \n" +
        "    f.flightTimeInMinutes AS totalDuration\n" +
        "UNION\n" +
        "MATCH (from:Airport)-[:LOCATED_IN]->(fromCity:City),\n" +
        "      (to:Airport)-[:LOCATED_IN]->(toCity:City),\n" +
        "      (middle:Airport)-[:LOCATED_IN]->(middleCity:City),\n" +
        "      (f1:Flight)-[:DEPARTS_FROM]->(from),\n" +
        "      (f1)-[:ARRIVES_AT]->(middle),\n" +
        "      (f2:Flight)-[:DEPARTS_FROM]->(middle),\n" +
        "      (f2)-[:ARRIVES_AT]->(to)\n" +
        "WHERE fromCity.name = $fromCity AND toCity.name = $toCity\n" +
        "  AND middleCity.name <> $fromCity AND middleCity.name <> $toCity\n" +
        "RETURN \n" +
        "    from.code AS fromAirport, \n" +
        "    to.code AS toAirport, \n" +
        "    COLLECT(f1.number) + COLLECT(f2.number) AS flights, \n" +
        "    f1.price + f2.price AS totalPrice, \n" +
        "    f1.flightTimeInMinutes + f2.flightTimeInMinutes AS totalDuration\n" +
        "ORDER BY totalPrice";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fromCity", fromCity);
        parameters.put("toCity", toCity);

        return neo4jClient.query(query)
                .bindAll(parameters)
                .fetch()
                .all()
                .stream()
                .map(record -> new FlightSearchResponse(
                    (String) record.get("fromAirport"),
                    (String) record.get("toAirport"),
                    (List<String>) record.get("flights"),
                    (Double) record.get("totalPrice"),
                    ((Long) record.get("totalDuration")).intValue()
                ))
                .collect(Collectors.toList());
    }


    @GetMapping("/search/flights/{fromCity}/{toCity}")
    public ResponseEntity<?> findFlightsB(@PathVariable String fromCity, @PathVariable String toCity) {
        List<FlightSearchResponse> flights = findFlightsBetweenCities(fromCity, toCity);

        
        if (flights.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flights not found.");
        }
        return ResponseEntity.ok(flights);
    }
    
       
    @PostMapping("/cleanup")
    public ResponseEntity<?> cleanupDatabase() {
        try {
            flightRepository.deleteAll();
            airportRepository.deleteAll();
            cityRepository.deleteAll();

            return new ResponseEntity<>("Cleanup successful.", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cleanup failed: " + e.getMessage());
        }
    }

    
}
