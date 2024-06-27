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

public class IncludeSkillWindow extends JDialog {

	@Getter
	private String skill;
	@Getter
	private String experience;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtSkill;
	private JTextField txtExperience;
	private JLabel lblSkill;
	private JLabel lblExperience;
	private JButton btnOk;
	private JButton btnCancelar;
	private JPanel buttonPane;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			LoginCandidateWindow dialog = new LoginCandidateWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IncludeSkillWindow() {

		setTitle("Incluir competência");
		setBounds(100, 100, 458, 194);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtSkill = new JTextField();
		txtSkill.setBounds(145, 32, 144, 23);
		contentPanel.add(txtSkill);
		txtSkill.setColumns(10);

		txtExperience = new JTextField();
		txtExperience.setBounds(145, 78, 144, 23);
		txtExperience.setColumns(10);
		contentPanel.add(txtExperience);

		lblSkill = new JLabel("Competência:");
		lblSkill.setBounds(25, 35, 110, 15);
		lblSkill.setFont(new Font("Arial", Font.PLAIN, 15));
		contentPanel.add(lblSkill);

		lblExperience = new JLabel("Experiência:");
		lblExperience.setBounds(25, 77, 110, 22);
		lblExperience.setFont(new Font("Arial", Font.PLAIN, 15));
		contentPanel.add(lblExperience);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skill = txtSkill.getText();
				experience = txtExperience.getText();
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
