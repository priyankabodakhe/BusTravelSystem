package com.bustravel.models;

import java.time.LocalDate;

public class Booking {
    private int bookingId;
    private int userId;
    private int busId;
    private LocalDate journeyDate;
    private int numSeats;
    private double totalAmount;
    private String status;

    public Booking(int userId, int busId, LocalDate journeyDate, int numSeats, double totalAmount) {
        this.userId = userId;
        this.busId = busId;
        this.journeyDate = journeyDate;
        this.numSeats = numSeats;
        this.totalAmount = totalAmount;
        this.status = "confirmed";
    }

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public int getUserId() { return userId; }
    public int getBusId() { return busId; }
    public LocalDate getJourneyDate() { return journeyDate; }
    public int getNumSeats() { return numSeats; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setStatus(String status) { this.status = status; }
}