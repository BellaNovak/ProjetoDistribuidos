package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import lombok.Getter;
import javax.swing.JLabel;
import java.awt.Font;

public class ConexaoWindow extends JDialog {

	@Getter
	private String serverIp;
	@Getter
	private String serverPorta;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtIp;
	private JButton btnOk;
	private JButton btnCancelar;
	private JPanel buttonPane;
	private JTextField txtPorta;
	private JLabel lblIp;
	private JLabel lblPorta;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			ConexaoWindow dialog = new ConexaoWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ConexaoWindow() {

		setTitle("Conexão");
		setBounds(100, 100, 378, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtIp = new JTextField();
		txtIp.setBounds(113, 45, 139, 23);
		contentPanel.add(txtIp);
		txtIp.setColumns(10);
		
		txtPorta = new JTextField();
		txtPorta.setColumns(10);
		txtPorta.setBounds(113, 94, 139, 23);
		contentPanel.add(txtPorta);
		
		lblIp = new JLabel("IP:");
		lblIp.setFont(new Font("Arial", Font.PLAIN, 15));
		lblIp.setBounds(49, 40, 30, 31);
		contentPanel.add(lblIp);
		
		lblPorta = new JLabel("Porta:");
		lblPorta.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPorta.setBounds(49, 89, 54, 31);
		contentPanel.add(lblPorta);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverIp = txtIp.getText();
				serverPorta = txtPorta.getText();
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
