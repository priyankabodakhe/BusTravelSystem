//Bus.java

package com.bustravel.models;

import java.sql.Time;

public class Bus {
    private int busId;
    private String busNumber;
    private String busName;
    private String sourceCity;
    private String destinationCity;
    private Time departureTime;
    private Time arrivalTime;
    private double price;
    private int totalSeats;
    private int availableSeats;
    private String busType;

    public Bus(int busId, String busNumber, String busName, String sourceCity, 
              String destinationCity, Time departureTime, Time arrivalTime,
              double price, int totalSeats, int availableSeats, String busType) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.busName = busName;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.busType = busType;
    }

    // Getters and Setters
    public int getBusId() { return busId; }
    public String getBusNumber() { return busNumber; }
    public String getBusName() { return busName; }
    public String getSourceCity() { return sourceCity; }
    public String getDestinationCity() { return destinationCity; }
    public Time getDepartureTime() { return departureTime; }
    public Time getArrivalTime() { return arrivalTime; }
    public double getPrice() { return price; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public String getBusType() { return busType; }

    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}