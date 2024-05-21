package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import lombok.Getter;

public class OpcoesRecruiterWindow extends JDialog {
	
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
			OpcoesRecruiterWindow dialog = new OpcoesRecruiterWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OpcoesRecruiterWindow() {
		
		setTitle("Opções da empresa");
		setBounds(100, 100, 487, 219);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "7";
			}
		});
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 17));
		btnLogin.setBounds(40, 31, 100, 30);
		contentPanel.add(btnLogin);

		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "8";
			}
		});
		btnLogout.setFont(new Font("Arial", Font.PLAIN, 17));
		btnLogout.setBounds(40, 91, 100, 30);
		contentPanel.add(btnLogout);

		btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "9";
			}
		});
		btnSignUp.setFont(new Font("Arial", Font.PLAIN, 17));
		btnSignUp.setBounds(176, 31, 100, 30);
		contentPanel.add(btnSignUp);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "12";
			}
		});
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 17));
		btnDelete.setBounds(319, 91, 100, 30);
		contentPanel.add(btnDelete);

		btnLookUp = new JButton("Look Up");
		btnLookUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "10";
			}
		});
		btnLookUp.setFont(new Font("Arial", Font.PLAIN, 17));
		btnLookUp.setBounds(176, 91, 100, 30);
		contentPanel.add(btnLookUp);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "11";
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
