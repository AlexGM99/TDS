package interfazGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Register {

	private JFrame frmAppchatregister;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_7;
	private JPasswordField passwordField;
	private JCheckBox chckbxPremium;
	private JButton btnRegister;
	private JButton btnIveAnAccount;
	private JLabel lblRepeatThePass;
	private JPasswordField passwordField_1;
	private JLabel lblNewLabel_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
					window.frmAppchatregister.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAppchatregister = new JFrame();
		frmAppchatregister.setMinimumSize(new Dimension(600, 350));
		frmAppchatregister.setTitle("APPCHAT-Register");
		frmAppchatregister.setBounds(100, 100, 450, 300);
		frmAppchatregister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 150, 0, 189, 0, 150, 0};
		gridBagLayout.rowHeights = new int[]{50, 0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmAppchatregister.getContentPane().setLayout(gridBagLayout);
		
		lblNewLabel_5 = new JLabel("REGISTER");
		lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 22));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 1;
		frmAppchatregister.getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblNewLabel = new JLabel("first name");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 2;
		frmAppchatregister.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 2;
		frmAppchatregister.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("second name");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 2;
		gbc_lblNewLabel_4.gridy = 3;
		frmAppchatregister.getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 3;
		frmAppchatregister.getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("birthday");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 4;
		frmAppchatregister.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 4;
		frmAppchatregister.getContentPane().add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		chckbxPremium = new JCheckBox("premium");
		GridBagConstraints gbc_chckbxPremium = new GridBagConstraints();
		gbc_chckbxPremium.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPremium.gridx = 4;
		gbc_chckbxPremium.gridy = 4;
		frmAppchatregister.getContentPane().add(chckbxPremium, gbc_chckbxPremium);
		
		JLabel lblNewLabel_2 = new JLabel("email");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 5;
		frmAppchatregister.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 5;
		frmAppchatregister.getContentPane().add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("phone");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 6;
		frmAppchatregister.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 6;
		frmAppchatregister.getContentPane().add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNick = new JLabel("nick");
		GridBagConstraints gbc_lblNick = new GridBagConstraints();
		gbc_lblNick.anchor = GridBagConstraints.WEST;
		gbc_lblNick.insets = new Insets(0, 0, 5, 5);
		gbc_lblNick.gridx = 2;
		gbc_lblNick.gridy = 7;
		frmAppchatregister.getContentPane().add(lblNick, gbc_lblNick);
		
		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 7;
		frmAppchatregister.getContentPane().add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
		JLabel lblPass = new JLabel("pass");
		GridBagConstraints gbc_lblPass = new GridBagConstraints();
		gbc_lblPass.anchor = GridBagConstraints.WEST;
		gbc_lblPass.insets = new Insets(0, 0, 5, 5);
		gbc_lblPass.gridx = 2;
		gbc_lblPass.gridy = 8;
		frmAppchatregister.getContentPane().add(lblPass, gbc_lblPass);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 3;
		gbc_passwordField.gridy = 8;
		frmAppchatregister.getContentPane().add(passwordField, gbc_passwordField);
		
		lblRepeatThePass = new JLabel("repeat the pass");
		GridBagConstraints gbc_lblRepeatThePass = new GridBagConstraints();
		gbc_lblRepeatThePass.anchor = GridBagConstraints.EAST;
		gbc_lblRepeatThePass.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeatThePass.gridx = 2;
		gbc_lblRepeatThePass.gridy = 9;
		frmAppchatregister.getContentPane().add(lblRepeatThePass, gbc_lblRepeatThePass);
		
		passwordField_1 = new JPasswordField();
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 3;
		gbc_passwordField_1.gridy = 9;
		frmAppchatregister.getContentPane().add(passwordField_1, gbc_passwordField_1);
		
		JLabel lblAvatar = new JLabel("avatar");
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.anchor = GridBagConstraints.WEST;
		gbc_lblAvatar.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar.gridx = 2;
		gbc_lblAvatar.gridy = 10;
		frmAppchatregister.getContentPane().add(lblAvatar, gbc_lblAvatar);
		
		textField_7 = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 5);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 3;
		gbc_textField_7.gridy = 10;
		frmAppchatregister.getContentPane().add(textField_7, gbc_textField_7);
		textField_7.setColumns(10);
		
		btnRegister = new JButton("register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO implementar mensaje de registro al controlador
				String firts = textField.getText();
				String second = textField_1.getText();
				String birth = textField_2.getText();
				String email = textField_3.getText();
				String phone = textField_4.getText();
				String nick = textField_5.getText();
				String pass = passwordField.getText();
				String passAgain = lblRepeatThePass.getText();
				String avatar = textField_7.getText();
			}
		});
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegister.gridx = 3;
		gbc_btnRegister.gridy = 11;
		frmAppchatregister.getContentPane().add(btnRegister, gbc_btnRegister);
		
		btnIveAnAccount = new JButton("I've an account");
		btnIveAnAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			//TODO cambiar a ventana de login
			}
		});
		GridBagConstraints gbc_btnIveAnAccount = new GridBagConstraints();
		gbc_btnIveAnAccount.insets = new Insets(0, 0, 5, 5);
		gbc_btnIveAnAccount.gridx = 3;
		gbc_btnIveAnAccount.gridy = 12;
		frmAppchatregister.getContentPane().add(btnIveAnAccount, gbc_btnIveAnAccount);
	}

}
