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

public class JobWindow {
	
	private JFrame frame;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private List<Map<String, String>> vagas;

    public JobWindow(List<Map<String, String>> vagas) {
        this.vagas = vagas;
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

        tableModel = new DefaultTableModel(new Object[]{"ID Empresa", "Habilidade", "Experiência", "Disponível"}, 0);
        resultTable = new JTable(tableModel);
        frame.getContentPane().add(new JScrollPane(resultTable), BorderLayout.CENTER);

        displayJobs();
        
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

    private void displayJobs() {
        tableModel.setRowCount(0);
        if (vagas != null) {
            for (Map<String, String> job : vagas) {
                tableModel.addRow(new Object[]{
                        job.get("id"),
                        job.get("skill"),
                        job.get("experience"),
                        job.get("available"),
                });
            }
        }
    }

}
