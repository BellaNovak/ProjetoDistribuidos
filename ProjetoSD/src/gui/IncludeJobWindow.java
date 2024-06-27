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

public class IncludeJobWindow extends JDialog {

	@Getter
	private String skill;
	@Getter
	private String experience;
	@Getter
	private String available;
	@Getter
	private String searchable;


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtSkill;
	private JTextField txtExperience;
	private JTextField txtAvailable;
	private JTextField txtSearchable;
	private JLabel lblSkill;
	private JLabel lblExperience;
	private JButton btnOk;
	private JButton btnCancelar;
	private JPanel buttonPane;
	private JLabel lblAvailable;
	private JLabel lblSearchable;
	
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

	public IncludeJobWindow() {
		
		setTitle("Incluir vaga");
		setBounds(100, 100, 458, 288);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtSkill = new JTextField();
		txtSkill.setBounds(157, 32, 144, 23);
		contentPanel.add(txtSkill);
		txtSkill.setColumns(10);

		txtExperience = new JTextField();
		txtExperience.setColumns(10);
		txtExperience.setBounds(157, 78, 144, 23);
		contentPanel.add(txtExperience);

		lblSkill = new JLabel("Competência:");
		lblSkill.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSkill.setBounds(34, 35, 101, 15);
		contentPanel.add(lblSkill);

		lblExperience = new JLabel("Experiência:");
		lblExperience.setFont(new Font("Arial", Font.PLAIN, 15));
		lblExperience.setBounds(34, 77, 101, 22);
		contentPanel.add(lblExperience);
		
		lblAvailable = new JLabel("Dispobilidade:");
		lblAvailable.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAvailable.setBounds(34, 128, 120, 22);
		contentPanel.add(lblAvailable);
		
		txtAvailable = new JTextField();
		txtAvailable.setColumns(10);
		txtAvailable.setBounds(157, 129, 144, 23);
		contentPanel.add(txtAvailable);
		
		lblSearchable = new JLabel("Divulgável:");
		lblSearchable.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchable.setBounds(34, 172, 120, 22);
		contentPanel.add(lblSearchable);
		
		txtSearchable = new JTextField();
		txtSearchable.setColumns(10);
		txtSearchable.setBounds(157, 173, 144, 23);
		contentPanel.add(txtSearchable);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skill = txtSkill.getText();
				experience = txtExperience.getText();
				available = txtAvailable.getText();
				searchable = txtSearchable.getText();
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
