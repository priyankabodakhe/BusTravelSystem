package com.bustravel.models;

public class User {
    private int userId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String role;

    public User(int userId, String username, String fullName, String email, String phone, String role) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }
}