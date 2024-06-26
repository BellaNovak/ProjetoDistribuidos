package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class ProfileWindow {

    private JFrame frame;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private List<Map<String, String>> perfis;

    public ProfileWindow(List<Map<String, String>> perfis) {
        this.perfis = perfis;
        initialize();
    }

    public void show() {
        EventQueue.invokeLater(() -> {
            try {
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    private void initialize() {
    	
        frame = new JFrame();
        frame.setTitle("Lista de canddiatos");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        tableModel = new DefaultTableModel(new Object[]{"ID", "Habilidade", "Experiência", "ID Usuário"}, 0);
        resultTable = new JTable(tableModel);
        frame.getContentPane().add(new JScrollPane(resultTable), BorderLayout.CENTER);

        displayProfiles();
        
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                frame.dispose(); 
            }
        });
        buttonPanel.add(okButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void displayProfiles() {
        tableModel.setRowCount(0);
        if (perfis != null) {
            for (Map<String, String> profile : perfis) {
                tableModel.addRow(new Object[]{
                        profile.get("id"),
                        profile.get("skill"),
                        profile.get("experience"),
                        profile.get("id_user")
                });
            }
        }
    }
    
}
