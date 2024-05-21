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

public class SignUpRecruiterWindow extends JDialog {

	@Getter
	private String email;
	@Getter
	private String password;
	@Getter
	private String name;
	@Getter
	private String industry;
	@Getter
	private String description;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JTextField txtIndustry;
	private JTextField txtDescription;
	private JLabel lblEmail;
	private JLabel lblPassword;
	private JButton btnOk;
	private JButton btnCancelar;
	private JPanel buttonPane;
	private JTextField txtName;
	private JLabel lblName;
	private JLabel lblIndustry;
	private JLabel lblDescription;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SignUpRecruiterWindow dialog = new SignUpRecruiterWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SignUpRecruiterWindow() {
		
		setTitle("Cadastro da empresa");
		setBounds(100, 100, 458, 324);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtEmail = new JTextField();
		txtEmail.setBounds(157, 32, 144, 23);
		contentPanel.add(txtEmail);
		txtEmail.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(157, 78, 144, 23);
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
		txtName.setBounds(157, 119, 144, 23);
		contentPanel.add(txtName);
		
		lblName = new JLabel("Nome:");
		lblName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblName.setBounds(76, 118, 59, 22);
		contentPanel.add(lblName);
		
		lblIndustry = new JLabel("Indústria:");
		lblIndustry.setFont(new Font("Arial", Font.PLAIN, 15));
		lblIndustry.setBounds(76, 161, 78, 22);
		contentPanel.add(lblIndustry);
		
		txtIndustry = new JTextField();
		txtIndustry.setColumns(10);
		txtIndustry.setBounds(157, 162, 144, 23);
		contentPanel.add(txtIndustry);
		
		lblDescription = new JLabel("Descrição:");
		lblDescription.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDescription.setBounds(76, 207, 78, 22);
		contentPanel.add(lblDescription);
		
		txtDescription = new JTextField();
		txtDescription.setColumns(10);
		txtDescription.setBounds(157, 208, 144, 23);
		contentPanel.add(txtDescription);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				email = txtEmail.getText();
				password = txtPassword.getText();
				name = txtName.getText();
				industry = txtIndustry.getText();
				description = txtDescription.getText();
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
