package com.example.neo4j_api.dto;

public class FlightInfoResponse {
    private String number;
    private String fromAirport;
    private String toAirport;
    private double price;
    private int flightTimeInMinutes;
    private String operator;
    private String fromCity;
    private String toCity;
    
    public FlightInfoResponse(String number, String fromAirport, String toAirport, double price, int flightTimeInMinutes, String operator, String fromCity, String toCity) {
        this.number = number;
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.price = price;
        this.flightTimeInMinutes = flightTimeInMinutes;

        this.operator = operator;
        this.fromCity = fromCity;
        this.toCity = toCity;
        
    }

    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
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

    public String getFromCity() {
        return fromCity;
    }
    
    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }


    public String getToCity() {
        return toCity;
    }
    
    public void setToCity(String toCity) {
        this.toCity = toCity;
    }
    
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    public int getFlightTimeInMinutes(){
        return flightTimeInMinutes;
    }
    
    public void setFlightTimeInMinutes(int flightTimeInMinutes){
        this.flightTimeInMinutes = flightTimeInMinutes;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }


    
}
