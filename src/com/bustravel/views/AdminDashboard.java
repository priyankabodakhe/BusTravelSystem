//AdminDashboard.java

package com.bustravel.views;

import com.bustravel.models.Bus;
import com.bustravel.models.Database;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTable busTable;
    private DefaultTableModel tableModel;

    public AdminDashboard() {
        setTitle("Bus Travel System - Admin Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton btnAdd = new JButton("Add New Bus");
        JButton btnDelete = new JButton("Delete Selected Bus");
        JButton btnRefresh = new JButton("Refresh List");
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        // Bus Table
        String[] columnNames = {"Bus ID", "Bus Number", "Name", "Source", "Destination", 
                               "Departure", "Arrival", "Price", "Total Seats", "Available", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        busTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(busTable);

        // Action Listeners
        btnAdd.addActionListener((ActionEvent e) -> {
            new AddBusDialog(this).setVisible(true);
        });

        btnDelete.addActionListener((ActionEvent e) -> {
            int selectedRow = busTable.getSelectedRow();
            if (selectedRow >= 0) {
                int busId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    if (Database.deleteBus(busId)) {
                        refreshBusList();
                        JOptionPane.showMessageDialog(this, 
                            "Bus deleted successfully", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Error deleting bus: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select a bus to delete", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRefresh.addActionListener((ActionEvent e) -> {
            refreshBusList();
        });

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        
        // Load data immediately
        refreshBusList();
    }

    public void refreshBusList() {
        try {
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Get all buses without filtering
            List<Bus> buses = Database.getAllBuses();
            
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            
            // Debug output
            System.out.println("Fetched " + buses.size() + " buses from database");
            
            for (Bus bus : buses) {
                tableModel.addRow(new Object[]{
                    bus.getBusId(),
                    bus.getBusNumber(),
                    bus.getBusName(),
                    bus.getSourceCity(),
                    bus.getDestinationCity(),
                    timeFormat.format(bus.getDepartureTime()),
                    timeFormat.format(bus.getArrivalTime()),
                    String.format("%.2f", bus.getPrice()),
                    bus.getTotalSeats(),
                    bus.getAvailableSeats(),
                    bus.getBusType()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error loading buses: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}