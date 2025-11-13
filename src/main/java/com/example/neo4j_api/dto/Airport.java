package com.example.neo4j_api.dto;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;


@Node("Airport")
public class Airport {
    @Id
    private String code;
    private String name;
    private int numberOfTerminals;
    private String address;

    @Relationship(type = "LOCATED_IN", direction = Relationship.Direction.OUTGOING)
    private City city;

    public Airport() {}

    public Airport(String code, String name, int numberOfTerminals, String address, City city) {
        this.code = code;
        this.name = name;
        this.numberOfTerminals = numberOfTerminals;
        this.address = address;
        this.city = city;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getNumberOfTerminals() {
        return numberOfTerminals;
    }
    
    public void setNumberOfTerminals(int numberOfTerminals) {
        this.numberOfTerminals = numberOfTerminals;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    


}

