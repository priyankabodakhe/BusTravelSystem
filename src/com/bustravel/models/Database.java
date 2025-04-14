//Database.java

package com.bustravel.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/travelms";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Bus Operations
    public static List<Bus> getAvailableBuses(String destination, double maxPrice) throws SQLException {
        List<Bus> buses = new ArrayList<>();
        String query = "SELECT * FROM buses WHERE destination_city = ? AND price <= ? AND available_seats > 0";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, destination);
            stmt.setDouble(2, maxPrice);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                buses.add(new Bus(
                    rs.getInt("bus_id"),
                    rs.getString("bus_number"),
                    rs.getString("bus_name"),
                    rs.getString("source_city"),
                    rs.getString("destination_city"),
                    rs.getTime("departure_time"),
                    rs.getTime("arrival_time"),
                    rs.getDouble("price"),
                    rs.getInt("total_seats"),
                    rs.getInt("available_seats"),
                    rs.getString("bus_type")
                ));
            }
        }
        return buses;
    }

    public static boolean addBus(Bus bus) throws SQLException {
        String query = "INSERT INTO buses (bus_number, bus_name, source_city, destination_city, " +
                      "departure_time, arrival_time, price, total_seats, available_seats, bus_type) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bus.getBusNumber());
            stmt.setString(2, bus.getBusName());
            stmt.setString(3, bus.getSourceCity());
            stmt.setString(4, bus.getDestinationCity());
            stmt.setTime(5, bus.getDepartureTime());
            stmt.setTime(6, bus.getArrivalTime());
            stmt.setDouble(7, bus.getPrice());
            stmt.setInt(8, bus.getTotalSeats());
            stmt.setInt(9, bus.getAvailableSeats());
            stmt.setString(10, bus.getBusType());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean deleteBus(int busId) throws SQLException {
        String query = "DELETE FROM buses WHERE bus_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, busId);
            return stmt.executeUpdate() > 0;
        }
    }

    // User Authentication
    public static User authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = SHA2(?, 256)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("role")
                );
            }
        }
        return null;
    }

    // Booking Operations
    public static boolean createBooking(Booking booking) throws SQLException {
        String query = "INSERT INTO bookings (user_id, bus_id, journey_date, num_seats, total_amount) " +
                      "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getBusId());
            stmt.setDate(3, Date.valueOf(booking.getJourneyDate()));
            stmt.setInt(4, booking.getNumSeats());
            stmt.setDouble(5, booking.getTotalAmount());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        booking.setBookingId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static List<Bus> getAllBuses() throws SQLException {
        List<Bus> buses = new ArrayList<>();
        String query = "SELECT * FROM buses";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                buses.add(new Bus(
                    rs.getInt("bus_id"),
                    rs.getString("bus_number"),
                    rs.getString("bus_name"),
                    rs.getString("source_city"),
                    rs.getString("destination_city"),
                    rs.getTime("departure_time"),
                    rs.getTime("arrival_time"),
                    rs.getDouble("price"),
                    rs.getInt("total_seats"),
                    rs.getInt("available_seats"),
                    rs.getString("bus_type")
                ));
            }
        }
        return buses;
    }
}