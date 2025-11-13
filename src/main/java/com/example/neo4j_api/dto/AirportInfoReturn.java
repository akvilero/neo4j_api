
package com.example.neo4j_api.dto;

public class AirportInfoReturn {
   
    private String code;
    private String name;
    private String city;
    private int numberOfTerminals;
    private String address;
    
    public AirportInfoReturn(){}

    public AirportInfoReturn(String code, String name, String city, int numberOfTerminals, String address) {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    


}
