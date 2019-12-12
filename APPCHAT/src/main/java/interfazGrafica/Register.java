package interfazGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.demo.DateChooserPanel;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Register {

	private JFrame frmAppchatregister;
	private JTextField firtsNamefield;
	private JTextField secondNamefield;
	private JTextField emailField;
	private JTextField phoneField;
	private JTextField nickField;
	private JPasswordField passwordField;
	private JCheckBox chckbxPremium;
	private JButton btnRegister;
	private JButton btnIveAnAccount;
	private JLabel lblRepeatThePass;
	private JPasswordField passwordField_1;
	private JLabel lblNewLabel_5;
	private JPanel avatarPhoto;
	private JDateChooser dateChooser;

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
		frmAppchatregister.setMinimumSize(new Dimension(600, 450));
		frmAppchatregister.setTitle("APPCHAT-Register");
		frmAppchatregister.setBounds(100, 100, 450, 300);
		frmAppchatregister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 150, 0, 189, 0, 150, 0};
		gridBagLayout.rowHeights = new int[]{50, 54, 145, 32, 0, 60, 0, 0, 0, 0, 0, 0, 0, 50, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmAppchatregister.getContentPane().setLayout(gridBagLayout);
		
		lblNewLabel_5 = new JLabel("REGISTER");
		lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD, 22));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 1;
		frmAppchatregister.getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblAvatar = new JLabel("avatar");
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.anchor = GridBagConstraints.WEST;
		gbc_lblAvatar.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar.gridx = 2;
		gbc_lblAvatar.gridy = 2;
		frmAppchatregister.getContentPane().add(lblAvatar, gbc_lblAvatar);
		
		avatarPhoto = new JPanel();
		avatarPhoto.setBorder(new LineBorder(Color.BLACK));
		avatarPhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO Ir al selector de imagenes del ordenador
			}
		});
		GridBagConstraints gbc_avatarPhoto = new GridBagConstraints();
		gbc_avatarPhoto.insets = new Insets(0, 0, 5, 5);
		gbc_avatarPhoto.fill = GridBagConstraints.BOTH;
		gbc_avatarPhoto.gridx = 3;
		gbc_avatarPhoto.gridy = 2;
		frmAppchatregister.getContentPane().add(avatarPhoto, gbc_avatarPhoto);
		
		JLabel lblNewLabel = new JLabel("first name");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 3;
		frmAppchatregister.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		firtsNamefield = new JTextField();
		GridBagConstraints gbc_firtsNamefield = new GridBagConstraints();
		gbc_firtsNamefield.insets = new Insets(0, 0, 5, 5);
		gbc_firtsNamefield.fill = GridBagConstraints.HORIZONTAL;
		gbc_firtsNamefield.gridx = 3;
		gbc_firtsNamefield.gridy = 3;
		frmAppchatregister.getContentPane().add(firtsNamefield, gbc_firtsNamefield);
		firtsNamefield.setColumns(10);
		
		chckbxPremium = new JCheckBox("premium");
		GridBagConstraints gbc_chckbxPremium = new GridBagConstraints();
		gbc_chckbxPremium.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPremium.gridx = 4;
		gbc_chckbxPremium.gridy = 3;
		frmAppchatregister.getContentPane().add(chckbxPremium, gbc_chckbxPremium);
		
		JLabel lblNewLabel_4 = new JLabel("second name");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 2;
		gbc_lblNewLabel_4.gridy = 4;
		frmAppchatregister.getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		secondNamefield = new JTextField();
		GridBagConstraints gbc_secondNamefield = new GridBagConstraints();
		gbc_secondNamefield.insets = new Insets(0, 0, 5, 5);
		gbc_secondNamefield.fill = GridBagConstraints.HORIZONTAL;
		gbc_secondNamefield.gridx = 3;
		gbc_secondNamefield.gridy = 4;
		frmAppchatregister.getContentPane().add(secondNamefield, gbc_secondNamefield);
		secondNamefield.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("birthday");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 5;
		frmAppchatregister.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		
		dateChooser = new JDateChooser();
	    GridBagConstraints gbc_dateChooser = new GridBagConstraints();
	    gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
	    gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
	    gbc_dateChooser.gridx = 3;
	    gbc_dateChooser.gridy = 5;
	    frmAppchatregister.getContentPane().add(dateChooser, gbc_dateChooser);
		
		JLabel lblNewLabel_2 = new JLabel("email");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 6;
		frmAppchatregister.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		emailField = new JTextField();
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.insets = new Insets(0, 0, 5, 5);
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.gridx = 3;
		gbc_emailField.gridy = 6;
		frmAppchatregister.getContentPane().add(emailField, gbc_emailField);
		emailField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("phone");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 7;
		frmAppchatregister.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		phoneField = new JTextField();
		GridBagConstraints gbc_phoneField = new GridBagConstraints();
		gbc_phoneField.insets = new Insets(0, 0, 5, 5);
		gbc_phoneField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneField.gridx = 3;
		gbc_phoneField.gridy = 7;
		frmAppchatregister.getContentPane().add(phoneField, gbc_phoneField);
		phoneField.setColumns(10);
		
		JLabel lblNick = new JLabel("nick");
		GridBagConstraints gbc_lblNick = new GridBagConstraints();
		gbc_lblNick.anchor = GridBagConstraints.WEST;
		gbc_lblNick.insets = new Insets(0, 0, 5, 5);
		gbc_lblNick.gridx = 2;
		gbc_lblNick.gridy = 8;
		frmAppchatregister.getContentPane().add(lblNick, gbc_lblNick);
		
		nickField = new JTextField();
		GridBagConstraints gbc_nickField = new GridBagConstraints();
		gbc_nickField.insets = new Insets(0, 0, 5, 5);
		gbc_nickField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nickField.gridx = 3;
		gbc_nickField.gridy = 8;
		frmAppchatregister.getContentPane().add(nickField, gbc_nickField);
		nickField.setColumns(10);
		
		JLabel lblPass = new JLabel("pass");
		GridBagConstraints gbc_lblPass = new GridBagConstraints();
		gbc_lblPass.anchor = GridBagConstraints.WEST;
		gbc_lblPass.insets = new Insets(0, 0, 5, 5);
		gbc_lblPass.gridx = 2;
		gbc_lblPass.gridy = 9;
		frmAppchatregister.getContentPane().add(lblPass, gbc_lblPass);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 3;
		gbc_passwordField.gridy = 9;
		frmAppchatregister.getContentPane().add(passwordField, gbc_passwordField);
		
		lblRepeatThePass = new JLabel("repeat the pass");
		GridBagConstraints gbc_lblRepeatThePass = new GridBagConstraints();
		gbc_lblRepeatThePass.anchor = GridBagConstraints.EAST;
		gbc_lblRepeatThePass.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeatThePass.gridx = 2;
		gbc_lblRepeatThePass.gridy = 10;
		frmAppchatregister.getContentPane().add(lblRepeatThePass, gbc_lblRepeatThePass);
		
		passwordField_1 = new JPasswordField();
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 3;
		gbc_passwordField_1.gridy = 10;
		frmAppchatregister.getContentPane().add(passwordField_1, gbc_passwordField_1);
		
		btnRegister = new JButton("register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO implementar mensaje de registro al controlador
				String firts = firtsNamefield.getText();
				String second = secondNamefield.getText();
				//Date birth = calendar.getDate();
				String email = emailField.getText();
				String phone = phoneField.getText();
				String nick = nickField.getText();
				String pass = passwordField.getText();
				String passAgain = lblRepeatThePass.getText();
				//TODO coger la foto de perfil por defecto si no está
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
		gbc_btnIveAnAccount.gridx = 4;
		gbc_btnIveAnAccount.gridy = 11;
		frmAppchatregister.getContentPane().add(btnIveAnAccount, gbc_btnIveAnAccount);
	}

}
