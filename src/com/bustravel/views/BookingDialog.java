//BookingDialog.java

package com.bustravel.views;

import com.bustravel.models.Booking;
import com.bustravel.models.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.sql.SQLException;

public class BookingDialog extends JDialog {
    private UserDashboard parent;
    private int userId;
    private int busId;

    public BookingDialog(UserDashboard parent, int userId, int busId) {
        super(parent, "Book Bus", true);
        this.parent = parent;
        this.userId = userId;
        this.busId = busId;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblJourneyDate = new JLabel("Journey Date (YYYY-MM-DD):");
        JTextField txtJourneyDate = new JTextField(LocalDate.now().toString());
        JLabel lblSeats = new JLabel("Number of Seats:");
        JSpinner spinnerSeats = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JLabel lblTotal = new JLabel("Total Amount:");
        JLabel lblTotalAmount = new JLabel();
        JButton btnBook = new JButton("Confirm Booking");
        JButton btnCancel = new JButton("Cancel");

        // Calculate initial total
        updateTotalAmount(spinnerSeats, lblTotalAmount);

        // Add listeners
        spinnerSeats.addChangeListener(e -> updateTotalAmount(spinnerSeats, lblTotalAmount));

        btnBook.addActionListener((ActionEvent e) -> {
            try {
                LocalDate journeyDate = LocalDate.parse(txtJourneyDate.getText());
                int numSeats = (int) spinnerSeats.getValue();
                double totalAmount = Double.parseDouble(lblTotalAmount.getText());
                
                Booking booking = new Booking(userId, busId, journeyDate, numSeats, totalAmount);
                if (Database.createBooking(booking)) {
                    JOptionPane.showMessageDialog(this, "Booking successful! Booking ID: " + booking.getBookingId(), 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error creating booking: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener((ActionEvent e) -> {
            dispose();
        });

        mainPanel.add(lblJourneyDate);
        mainPanel.add(txtJourneyDate);
        mainPanel.add(lblSeats);
        mainPanel.add(spinnerSeats);
        mainPanel.add(lblTotal);
        mainPanel.add(lblTotalAmount);
        mainPanel.add(new JLabel());
        mainPanel.add(btnBook);
        mainPanel.add(new JLabel());
        mainPanel.add(btnCancel);

        add(mainPanel);
    }

    private void updateTotalAmount(JSpinner spinnerSeats, JLabel lblTotalAmount) {
        // In a real app, you would fetch the bus price from database
        // Here we'll just use a placeholder value
        double pricePerSeat = 500.00; // Should be fetched from database
        int seats = (int) spinnerSeats.getValue();
        lblTotalAmount.setText(String.format("%.2f", pricePerSeat * seats));
    }
}