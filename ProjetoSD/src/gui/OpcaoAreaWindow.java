package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import lombok.Getter;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OpcaoAreaWindow extends JDialog {
	
	private String op;
	@Getter
	private String opFinal;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnCandidato;
	private JButton btnEmpresa;
	private JButton btnOk;
	private JButton btnCancelar;
	private JPanel buttonPane;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			OpcaoAreaWindow dialog = new OpcaoAreaWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OpcaoAreaWindow() {
		
		setTitle("Opção da área");
		setBounds(100, 100, 406, 197);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		btnCandidato = new JButton("Candidato");
		btnCandidato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "1";
			}
		});
		btnCandidato.setFont(new Font("Arial", Font.PLAIN, 17));
		btnCandidato.setBounds(54, 46, 115, 35);
		contentPanel.add(btnCandidato);
		
		btnEmpresa = new JButton("Empresa");
		btnEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "2";
			}
		});
		btnEmpresa.setFont(new Font("Arial", Font.PLAIN, 17));
		btnEmpresa.setBounds(212, 46, 115, 35);
		contentPanel.add(btnEmpresa);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opFinal = op;
				dispose();
			}
		});
		btnOk.setActionCommand("OK");
		buttonPane.add(btnOk);
		getRootPane().setDefaultButton(btnOk);

		btnCancelar = new JButton("Cancel");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setActionCommand("Cancel");
		buttonPane.add(btnCancelar);

	}
}
