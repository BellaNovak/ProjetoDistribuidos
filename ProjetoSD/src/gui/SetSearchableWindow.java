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

public class SetSearchableWindow extends JDialog {

	@Getter
	private String id;
	@Getter
	private String available;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	private JTextField txtSearchable;
	private JLabel lblId;
	private JLabel lblSearchable;
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

	public SetSearchableWindow() {

		setTitle("Atualizar divulgável");
		setBounds(100, 100, 458, 194);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtId = new JTextField();
		txtId.setBounds(187, 32, 144, 23);
		contentPanel.add(txtId);
		txtId.setColumns(10);

		txtSearchable = new JTextField();
		txtSearchable.setColumns(10);
		txtSearchable.setBounds(187, 78, 144, 23);
		contentPanel.add(txtSearchable);

		lblId = new JLabel("Id:");
		lblId.setFont(new Font("Arial", Font.PLAIN, 15));
		lblId.setBounds(33, 35, 59, 15);
		contentPanel.add(lblId);

		lblSearchable = new JLabel("Divulgável (YES/NO):");
		lblSearchable.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchable.setBounds(33, 77, 144, 22);
		contentPanel.add(lblSearchable);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id = txtId.getText();
				available = txtSearchable.getText();
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
