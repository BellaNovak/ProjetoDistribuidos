package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class ServerWindow extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private final JList<String> userList;
	private final DefaultListModel<String> listModel;

	public ServerWindow(Set<String> loggedUsers) {
		
        setTitle("Logged Users");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        add(scrollPane, BorderLayout.CENTER);
        updateLoggedUsers(loggedUsers);
    }

	public void updateLoggedUsers(Set<String> loggedUsers) {
		
        SwingUtilities.invokeLater(() -> {
            listModel.clear();
            for (String user : loggedUsers) {
                listModel.addElement(user);
            }
        });
    }
}
