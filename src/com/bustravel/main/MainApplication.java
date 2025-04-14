//MainApplication.java

package com.bustravel.main;

import com.bustravel.views.LoginFrame;
import javax.swing.UIManager;

public class MainApplication {
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Show login frame
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}