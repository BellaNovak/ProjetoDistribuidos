package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import lombok.Getter;

public class SignUpCandidateWindow extends JDialog {
	
	@Getter
	private String email;
	@Getter
	private String password;
	@Getter
	private String name;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JLabel lblEmail;
	private JLabel lblPassword;
	private JButton btnOk;
	private JButton btnCancelar;
	private JPanel buttonPane;
	private JTextField txtName;
	private JLabel lblName;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SignUpCandidateWindow dialog = new SignUpCandidateWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SignUpCandidateWindow() {
		
		setTitle("Cadastro do candidato");
		setBounds(100, 100, 458, 230);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtEmail = new JTextField();
		txtEmail.setBounds(145, 32, 144, 23);
		contentPanel.add(txtEmail);
		txtEmail.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(145, 78, 144, 23);
		contentPanel.add(txtPassword);

		lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmail.setBounds(76, 35, 59, 15);
		contentPanel.add(lblEmail);

		lblPassword = new JLabel("Senha:");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPassword.setBounds(76, 77, 59, 22);
		contentPanel.add(lblPassword);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(145, 129, 144, 23);
		contentPanel.add(txtName);
		
		lblName = new JLabel("Nome:");
		lblName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblName.setBounds(76, 128, 59, 22);
		contentPanel.add(lblName);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				email = txtEmail.getText();
				password = txtPassword.getText();
				name = txtName.getText();
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

