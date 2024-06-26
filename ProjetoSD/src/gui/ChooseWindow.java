package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ChooseWindow {
	
	private JFrame frame;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private List<Map<String, String>> empresas;

    public ChooseWindow(List<Map<String, String>> empresas) {
        this.empresas = empresas;
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
        frame.setTitle("Lista de vagas");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        tableModel = new DefaultTableModel(new Object[]{"Nome", "Indústria", "Email", "Descrição"}, 0);
        resultTable = new JTable(tableModel);
        frame.getContentPane().add(new JScrollPane(resultTable), BorderLayout.CENTER);

        displayChoose();
        
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

    private void displayChoose() {
        tableModel.setRowCount(0);
        if (empresas != null) {
            for (Map<String, String> empresa : empresas) {
                tableModel.addRow(new Object[]{
                        empresa.get("name"),
                        empresa.get("industry"),
                        empresa.get("email"),
                        empresa.get("description"),
                });
            }
        }
    }


}
