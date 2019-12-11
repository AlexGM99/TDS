package interfazGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class LogIn {

	private JFrame frmAppchatlogin;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JLabel label;
	private JButton btnRegister;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn window = new LogIn();
					window.frmAppchatlogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LogIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAppchatlogin = new JFrame();
		frmAppchatlogin.setTitle("APPCHAT-LogIn");
		frmAppchatlogin.setMinimumSize(new Dimension(500, 500));
		frmAppchatlogin.setLocation(new Point(400, 100));
		frmAppchatlogin.setSize(new Dimension(500, 500));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0, 0, 0, 0, 0, 100, 0};
		gridBagLayout.rowHeights = new int[]{50, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmAppchatlogin.getContentPane().setLayout(gridBagLayout);
		
		lblNewLabel = new JLabel("WELCOME BACK!");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 26));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 0;
		frmAppchatlogin.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblTlf = new JLabel("tlf");
		GridBagConstraints gbc_lblTlf = new GridBagConstraints();
		gbc_lblTlf.insets = new Insets(0, 0, 5, 5);
		gbc_lblTlf.anchor = GridBagConstraints.EAST;
		gbc_lblTlf.gridx = 3;
		gbc_lblTlf.gridy = 2;
		frmAppchatlogin.getContentPane().add(lblTlf, gbc_lblTlf);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 4;
		gbc_textField.gridy = 2;
		frmAppchatlogin.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPass = new JLabel("pass");
		GridBagConstraints gbc_lblPass = new GridBagConstraints();
		gbc_lblPass.anchor = GridBagConstraints.EAST;
		gbc_lblPass.insets = new Insets(0, 0, 5, 5);
		gbc_lblPass.gridx = 3;
		gbc_lblPass.gridy = 3;
		frmAppchatlogin.getContentPane().add(lblPass, gbc_lblPass);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 4;
		gbc_passwordField.gridy = 3;
		frmAppchatlogin.getContentPane().add(passwordField, gbc_passwordField);
		
		btnLogin = new JButton("logIn");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//TODO funcionalidad del login
				String usuario = textField.getText();
				String contrasena = passwordField.getText();
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 5;
		gbc_btnLogin.gridy = 4;
		frmAppchatlogin.getContentPane().add(btnLogin, gbc_btnLogin);
		
		label = new JLabel("You still don’t have an account?");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 5;
		gbc_label.gridy = 5;
		frmAppchatlogin.getContentPane().add(label, gbc_label);
		
		btnRegister = new JButton("Register");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			//TODO cambiar a la ventana de registro
			}
		});
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegister.gridx = 5;
		gbc_btnRegister.gridy = 6;
		frmAppchatlogin.getContentPane().add(btnRegister, gbc_btnRegister);
	}

}
