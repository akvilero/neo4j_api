package com.example.neo4j_api.dto;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Flight")
public class Flight {
    @Id
    private String number;
    private double price;
    private int flightTimeInMinutes;
    private String operator;

    @Relationship(type = "DEPARTS_FROM", direction = Relationship.Direction.OUTGOING)
    private Airport fromAirport;

    @Relationship(type = "ARRIVES_AT", direction = Relationship.Direction.OUTGOING)
    private Airport toAirport;

    public Flight() {}

    public Flight(String number, double price, int flightTimeInMinutes, String operator, Airport fromAirport, Airport toAirport) {
        this.number = number;
        this.price = price;
        this.flightTimeInMinutes = flightTimeInMinutes;
        this.operator = operator;
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFlightTimeInMinutes() {
        return flightTimeInMinutes;
    }

    public void setFlightTimeInMinutes(int flightTimeInMinutes) {
        this.flightTimeInMinutes = flightTimeInMinutes;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(Airport fromAirport) {
        this.fromAirport = fromAirport;
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public void setToAirport(Airport toAirport) {
        this.toAirport = toAirport;
    }
    
}
