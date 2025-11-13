package com.example.neo4j_api.dto;

import java.util.List;
public class FlightSearchResponse {
    private String fromAirport;
    private String toAirport;
    private List<String> flights;
    private double price;
    private int flightTimeInMinutes;

    public FlightSearchResponse(String fromAirport, String toAirport, List<String> flights, double price, int flightTimeInMinutes) {
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.flights = flights;
        this.price = price;
        this.flightTimeInMinutes = flightTimeInMinutes;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public List<String> getFlights() {
        return flights;
    }

    public void setFlights(List<String> flights) {
        this.flights = flights;
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
}


