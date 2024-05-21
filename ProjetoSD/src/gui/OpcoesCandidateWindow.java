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

public class OpcoesCandidateWindow extends JDialog {
	
	private String op;
	@Getter
	private String opFinal;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnOk;
	private JButton btnCancelar;
	private JButton btnLogin;
	private JButton btnLogout;
	private JButton btnSignUp;
	private JButton btnLookUp;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JPanel buttonPane;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			OpcoesCandidateWindow dialog = new OpcoesCandidateWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OpcoesCandidateWindow() {

		setTitle("Opções do candidato");
		setBounds(100, 100, 487, 219);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "1";
			}
		});
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 17));
		btnLogin.setBounds(40, 31, 100, 30);
		contentPanel.add(btnLogin);

		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "2";
			}
		});
		btnLogout.setFont(new Font("Arial", Font.PLAIN, 17));
		btnLogout.setBounds(40, 91, 100, 30);
		contentPanel.add(btnLogout);

		btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "3";
			}
		});
		btnSignUp.setFont(new Font("Arial", Font.PLAIN, 17));
		btnSignUp.setBounds(176, 31, 100, 30);
		contentPanel.add(btnSignUp);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "6";
			}
		});
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 17));
		btnDelete.setBounds(319, 91, 100, 30);
		contentPanel.add(btnDelete);

		btnLookUp = new JButton("Look Up");
		btnLookUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "4";
			}
		});
		btnLookUp.setFont(new Font("Arial", Font.PLAIN, 17));
		btnLookUp.setBounds(176, 91, 100, 30);
		contentPanel.add(btnLookUp);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "5";
			}
		});
		btnUpdate.setFont(new Font("Arial", Font.PLAIN, 17));
		btnUpdate.setBounds(319, 31, 100, 30);
		contentPanel.add(btnUpdate);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("Confirmar");
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
