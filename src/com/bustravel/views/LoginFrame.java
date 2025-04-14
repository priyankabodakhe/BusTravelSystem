//LoginFrame.java

package com.bustravel.views;

import com.bustravel.models.Database;
import com.bustravel.models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Time;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Bus Travel System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUser = new JLabel("Username:");
        JTextField txtUser = new JTextField();
        JLabel lblPass = new JLabel("Password:");
        JPasswordField txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        btnLogin.addActionListener((ActionEvent e) -> {
            String username = txtUser.getText();
            String password = new String(txtPass.getPassword());
            
            try {
                User user = Database.authenticateUser(username, password);
                if (user != null) {
                    if (user.isAdmin()) {
                        new AdminDashboard().setVisible(true);
                    } else {
                        new UserDashboard(user).setVisible(true);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(lblUser);
        panel.add(txtUser);
        panel.add(lblPass);
        panel.add(txtPass);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);
    }
}