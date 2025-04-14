//UserDashboard.java

package com.bustravel.views;

import com.bustravel.models.Bus;
import com.bustravel.models.Database;
import com.bustravel.models.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserDashboard extends JFrame {
    private User currentUser;
    private JTable busTable;
    private DefaultTableModel tableModel;

    public UserDashboard(User user) {
        this.currentUser = user;
        setTitle("Bus Travel System - User Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JTextField txtDestination = new JTextField(15);
        JTextField txtMaxPrice = new JTextField(10);
        JButton btnSearch = new JButton("Search Buses");
        
        searchPanel.add(new JLabel("Destination:"));
        searchPanel.add(txtDestination);
        searchPanel.add(new JLabel("Max Price:"));
        searchPanel.add(txtMaxPrice);
        searchPanel.add(btnSearch);

        // Bus Table
        String[] columnNames = {"Bus ID", "Bus Number", "Name", "Source", "Destination", 
                               "Departure", "Arrival", "Price", "Seats", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0);
        busTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(busTable);

        // Booking Panel
        JPanel bookingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnBook = new JButton("Book Selected Bus");
        bookingPanel.add(btnBook);

        // Action Listeners
        btnSearch.addActionListener((ActionEvent e) -> {
            try {
                String destination = txtDestination.getText();
                double maxPrice = Double.parseDouble(txtMaxPrice.getText());
                searchBuses(destination, maxPrice);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBook.addActionListener((ActionEvent e) -> {
            int selectedRow = busTable.getSelectedRow();
            if (selectedRow >= 0) {
                int busId = (int) tableModel.getValueAt(selectedRow, 0);
                new BookingDialog(this, currentUser.getUserId(), busId).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a bus first", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bookingPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void searchBuses(String destination, double maxPrice) throws SQLException {
        List<Bus> buses = Database.getAvailableBuses(destination, maxPrice);
        tableModel.setRowCount(0); // Clear existing rows
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        for (Bus bus : buses) {
            tableModel.addRow(new Object[]{
                bus.getBusId(),
                bus.getBusNumber(),
                bus.getBusName(),
                bus.getSourceCity(),
                bus.getDestinationCity(),
                timeFormat.format(bus.getDepartureTime()),
                timeFormat.format(bus.getArrivalTime()),
                bus.getPrice(),
                bus.getAvailableSeats() + "/" + bus.getTotalSeats(),
                bus.getBusType()
            });
        }
    }
}