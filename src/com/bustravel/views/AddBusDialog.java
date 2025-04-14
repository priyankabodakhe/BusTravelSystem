//AddBusDialog.java

package com.bustravel.views;

import com.bustravel.models.Bus;
import com.bustravel.models.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AddBusDialog extends JDialog {
    private AdminDashboard parent;

    public AddBusDialog(AdminDashboard parent) {
        super(parent, "Add New Bus", true);
        this.parent = parent;
        setSize(500, 400);
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form fields
        JTextField txtBusNumber = new JTextField();
        JTextField txtBusName = new JTextField();
        JTextField txtSource = new JTextField();
        JTextField txtDestination = new JTextField();
        JTextField txtDeparture = new JTextField("HH:MM");
        JTextField txtArrival = new JTextField("HH:MM");
        JTextField txtPrice = new JTextField();
        JTextField txtTotalSeats = new JTextField();
        JComboBox<String> cmbBusType = new JComboBox<>(new String[]{"AC", "Non-AC", "Sleeper", "Semi-Sleeper"});
        
        JButton btnSubmit = new JButton("Add Bus");
        JButton btnCancel = new JButton("Cancel");

        // Add components to panel
        mainPanel.add(new JLabel("Bus Number:"));
        mainPanel.add(txtBusNumber);
        mainPanel.add(new JLabel("Bus Name:"));
        mainPanel.add(txtBusName);
        mainPanel.add(new JLabel("Source City:"));
        mainPanel.add(txtSource);
        mainPanel.add(new JLabel("Destination City:"));
        mainPanel.add(txtDestination);
        mainPanel.add(new JLabel("Departure Time (HH:MM):"));
        mainPanel.add(txtDeparture);
        mainPanel.add(new JLabel("Arrival Time (HH:MM):"));
        mainPanel.add(txtArrival);
        mainPanel.add(new JLabel("Price:"));
        mainPanel.add(txtPrice);
        mainPanel.add(new JLabel("Total Seats:"));
        mainPanel.add(txtTotalSeats);
        mainPanel.add(new JLabel("Bus Type:"));
        mainPanel.add(cmbBusType);
        mainPanel.add(btnSubmit);
        mainPanel.add(btnCancel);

        // Action Listeners
        btnSubmit.addActionListener((ActionEvent e) -> {
            try {
                // Parse times with seconds added
                LocalTime departure = LocalTime.parse(txtDeparture.getText() + ":00");
                LocalTime arrival = LocalTime.parse(txtArrival.getText() + ":00");
                
                Bus newBus = new Bus(
                    0, // ID will be auto-generated
                    txtBusNumber.getText(),
                    txtBusName.getText(),
                    txtSource.getText(),
                    txtDestination.getText(),
                    Time.valueOf(departure),
                    Time.valueOf(arrival),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtTotalSeats.getText()),
                    Integer.parseInt(txtTotalSeats.getText()), // Available seats = total seats initially
                    (String) cmbBusType.getSelectedItem()
                );
                
                if (Database.addBus(newBus)) {
                    parent.refreshBusList();
                    dispose();
                    JOptionPane.showMessageDialog(this, 
                        "Bus added successfully", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid time format (use HH:MM like 08:30 or 14:45)", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid number format in price or seats", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Database error: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener((ActionEvent e) -> {
            dispose();
        });

        add(mainPanel);
    }
}