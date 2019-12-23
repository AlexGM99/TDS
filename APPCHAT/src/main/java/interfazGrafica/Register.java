package interfazGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import com.toedter.calendar.JDateChooser;

import controlador.ControladorVistaAppChat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.BorderLayout;

public class Register implements InterfazVistas {

	private JFrame frmAppchatregister;
	private JTextField firtsNamefield;
	private JTextField secondNamefield;
	private JTextField emailField;
	private JTextField phoneField;
	private JTextField nickField;
	private JPasswordField passwordField;
	private JButton btnRegister;
	private JButton btnIveAnAccount;
	private JLabel lblRepeatThePass;
	private JPasswordField passwordField_1;
	private JLabel lblNewLabel_5;
	private JPanel avatarPhoto;
	private JDateChooser dateChooser;
	private JLabel lblCampoObligatorioError;
	private JLabel lblPassMustMatch;
	private JLabel lblPhoto;
	private JLabel lblWrongPicture;
	private JLabel lblGreeting;
	private JTextField greetingField;
	
	private String rutaAbsolutaAlaImagen="";//si está vacio será la por defecto
	
	private ControladorVistaAppChat controlador;
	private JLabel lblWrongMailFormat;
	Pattern pattern = Pattern
            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	private JButton btnClear;
	
	private boolean checkFields() {
		boolean salida=true;
		boolean passMatch = true;
		boolean mailmatch = true;
		/*borrar todos los errores en pantalla*/
		ocultarErrores();
		String pass1=new String(passwordField.getPassword());
		String pass2=new String(passwordField_1.getPassword());
		if(firtsNamefield.getText().trim().isEmpty()) {
			salida = false;
		}
		if(secondNamefield.getText().trim().isEmpty()) {
			salida = false;
		}
		if(emailField.getText().trim().isEmpty()) {
			salida = false;
		}
		if (!emailField.getText().trim().isEmpty()) {
			Matcher mather = pattern.matcher(emailField.getText());
			if (mather.find() == false) {
				lblWrongMailFormat.setVisible(true);
				mailmatch = false;
			}
		}
		if(phoneField.getText().trim().isEmpty()) {
			salida = false;
		}
		if(nickField.getText().trim().isEmpty()) {
			salida = false;
		}
		if(pass1.trim().isEmpty()) {
			salida = false;
		}
		if(pass2.trim().isEmpty()) {
			salida = false;
		}
		if(!pass1.equals(pass2)) {
			passMatch = false;
			lblPassMustMatch.setVisible(true);
		}
		if (!salida) lblCampoObligatorioError.setVisible(true);
		return salida&&passMatch&&mailmatch;
	}
	
	private void ocultarErrores() {
		lblCampoObligatorioError.setVisible(false);
		lblPassMustMatch.setVisible(false);
		lblWrongMailFormat.setVisible(false);
	}
	
	private void clearFields() {
		firtsNamefield.setText(null);
		secondNamefield.setText(null);
		dateChooser.setDate(null);
		emailField.setText(null);
		phoneField.setText(null);
		nickField.setText(null);
		greetingField.setText(null);
		passwordField.setText(null);
		passwordField_1.setText(null);
	}

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
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
	}*/

	/**
	 * Create the application.
	 */
	public Register(ControladorVistaAppChat controlador) {
		initialize();
		this.controlador = controlador;
		this.frmAppchatregister.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAppchatregister = new JFrame();
		frmAppchatregister.setMinimumSize(new Dimension(700, 600));
		frmAppchatregister.setTitle("APPCHAT-Register");
		frmAppchatregister.setBounds(100, 100, 450, 300);
		frmAppchatregister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAppchatregister.setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 150, 0, 189, 0, 150, 0};
		gridBagLayout.rowHeights = new int[]{50, 54, 145, 32, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		avatarPhoto.setMaximumSize(new Dimension(200, 200));
		avatarPhoto.setBorder(new LineBorder(Color.BLACK));
		avatarPhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TO DO Ir al selector de imagenes del ordenador
				lblWrongPicture.setVisible(false);
				Scanner entrada = null;
		        JFileChooser fileChooser = new JFileChooser();
		        FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png"); 
		        fileChooser.setFileFilter(imgFilter);
		        fileChooser.showOpenDialog(fileChooser);
		        try {
		            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
		            rutaAbsolutaAlaImagen = ruta;
		            File f = new File(ruta);
		            entrada = new Scanner(f);
		            while (entrada.hasNext()) {
		                System.out.println(entrada.nextLine());
		            }
		            BufferedImage myPicture;
		            try { 
		    			myPicture = ImageIO.read(f);			
		    			Image aux=myPicture.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		    			lblPhoto.setIcon(new ImageIcon(aux));
		    			lblPhoto.repaint();
		    		} catch (IOException e1) {
		    			//e1.printStackTrace();
		    			lblWrongPicture.setVisible(true);
		    		}
		        } catch (FileNotFoundException e0) {
		            //System.out.println(e0.getMessage());
		            lblWrongPicture.setVisible(true);
		        } catch (NullPointerException e1) {
		            //System.out.println("No se ha seleccionado ningún fichero");
		        } catch (Exception e2) {
		            //System.out.println(e2.getMessage());
		        	lblWrongPicture.setVisible(true);
		        } finally {
		            if (entrada != null) {
		                entrada.close();
		            }
		        }
			}
			
		});
		GridBagConstraints gbc_avatarPhoto = new GridBagConstraints();
		gbc_avatarPhoto.fill = GridBagConstraints.BOTH;
		gbc_avatarPhoto.insets = new Insets(0, 0, 5, 5);
		gbc_avatarPhoto.gridx = 3;
		gbc_avatarPhoto.gridy = 2;
		frmAppchatregister.getContentPane().add(avatarPhoto, gbc_avatarPhoto);
		avatarPhoto.setLayout(new BorderLayout(0, 0));
		
		lblPhoto = new JLabel("");
		lblPhoto.setIcon(new ImageIcon(Register.class.getResource("/ImagensDefault/usuarioDefecto.png")));
		avatarPhoto.add(lblPhoto, BorderLayout.CENTER);
		
		lblWrongPicture = new JLabel("Wrong picture");
		lblWrongPicture.setForeground(Color.RED);
		GridBagConstraints gbc_lblWrongPicture = new GridBagConstraints();
		gbc_lblWrongPicture.anchor = GridBagConstraints.SOUTH;
		gbc_lblWrongPicture.insets = new Insets(0, 0, 5, 5);
		gbc_lblWrongPicture.gridx = 4;
		gbc_lblWrongPicture.gridy = 2;
		frmAppchatregister.getContentPane().add(lblWrongPicture, gbc_lblWrongPicture);
		lblWrongPicture.setVisible(false);
		
		JLabel lblNewLabel = new JLabel("first name*");
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
		
		JLabel lblNewLabel_4 = new JLabel("second name*");
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
		
		JLabel lblNewLabel_2 = new JLabel("email*");
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
		
		lblWrongMailFormat = new JLabel("Wrong mail format");
		lblWrongMailFormat.setForeground(Color.RED);
		GridBagConstraints gbc_lblWrongMailFormat = new GridBagConstraints();
		gbc_lblWrongMailFormat.insets = new Insets(0, 0, 5, 5);
		gbc_lblWrongMailFormat.gridx = 4;
		gbc_lblWrongMailFormat.gridy = 6;
		frmAppchatregister.getContentPane().add(lblWrongMailFormat, gbc_lblWrongMailFormat);
		lblWrongMailFormat.setVisible(false);
		
		JLabel lblNewLabel_3 = new JLabel("phone*");
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
		
		JLabel lblNick = new JLabel("nick*");
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
		
		lblGreeting = new JLabel("greeting");
		GridBagConstraints gbc_lblGreeting = new GridBagConstraints();
		gbc_lblGreeting.anchor = GridBagConstraints.WEST;
		gbc_lblGreeting.insets = new Insets(0, 0, 5, 5);
		gbc_lblGreeting.gridx = 2;
		gbc_lblGreeting.gridy = 9;
		frmAppchatregister.getContentPane().add(lblGreeting, gbc_lblGreeting);
		
		greetingField = new JTextField();
		GridBagConstraints gbc_greetingField = new GridBagConstraints();
		gbc_greetingField.insets = new Insets(0, 0, 5, 5);
		gbc_greetingField.fill = GridBagConstraints.HORIZONTAL;
		gbc_greetingField.gridx = 3;
		gbc_greetingField.gridy = 9;
		frmAppchatregister.getContentPane().add(greetingField, gbc_greetingField);
		greetingField.setColumns(10);
		
		JLabel lblPass = new JLabel("pass*");
		GridBagConstraints gbc_lblPass = new GridBagConstraints();
		gbc_lblPass.anchor = GridBagConstraints.WEST;
		gbc_lblPass.insets = new Insets(0, 0, 5, 5);
		gbc_lblPass.gridx = 2;
		gbc_lblPass.gridy = 10;
		frmAppchatregister.getContentPane().add(lblPass, gbc_lblPass);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 3;
		gbc_passwordField.gridy = 10;
		frmAppchatregister.getContentPane().add(passwordField, gbc_passwordField);
		
		lblRepeatThePass = new JLabel("repeat the pass*");
		GridBagConstraints gbc_lblRepeatThePass = new GridBagConstraints();
		gbc_lblRepeatThePass.anchor = GridBagConstraints.EAST;
		gbc_lblRepeatThePass.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeatThePass.gridx = 2;
		gbc_lblRepeatThePass.gridy = 11;
		frmAppchatregister.getContentPane().add(lblRepeatThePass, gbc_lblRepeatThePass);
		
		passwordField_1 = new JPasswordField();
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 3;
		gbc_passwordField_1.gridy = 11;
		frmAppchatregister.getContentPane().add(passwordField_1, gbc_passwordField_1);
		
		btnRegister = new JButton("register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TO DO implementar mensaje de registro al controlador
				if(checkFields()) {
					String firts = firtsNamefield.getText();
					String second = secondNamefield.getText();
					Date birth = dateChooser.getDate();
					String email = emailField.getText();
					String phone = phoneField.getText();
					String nick = nickField.getText();
					String greeting = greetingField.getText();
					String pass = passwordField.getPassword().toString();
					String passAgain = passwordField_1.getPassword().toString();
					ImageIcon fotoPerfil = (ImageIcon)lblPhoto.getIcon();
					String registrado =
							controlador.RegisterUser(firts+" "+second, birth, email, phone, nick, pass, 
									rutaAbsolutaAlaImagen, greeting);
					if (!registrado.equals("")) {
						JOptionPane.showMessageDialog(dateChooser, "Something wrong on the register\ncheck: " +registrado, "Mensaje del servidor", 0);
					}
				}
				//TO DO coger la foto de perfil por defecto o la añadida por el usuario.
				//TO DO enviar registro
			}
		});
		
		lblPassMustMatch = new JLabel("pass must match");
		lblPassMustMatch.setForeground(Color.RED);
		GridBagConstraints gbc_lblPassMustMatch = new GridBagConstraints();
		gbc_lblPassMustMatch.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassMustMatch.gridx = 4;
		gbc_lblPassMustMatch.gridy = 11;
		frmAppchatregister.getContentPane().add(lblPassMustMatch, gbc_lblPassMustMatch);
		lblPassMustMatch.setVisible(false);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearFields();
			}
		});
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.insets = new Insets(0, 0, 5, 5);
		gbc_btnClear.gridx = 2;
		gbc_btnClear.gridy = 12;
		frmAppchatregister.getContentPane().add(btnClear, gbc_btnClear);
		
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegister.gridx = 3;
		gbc_btnRegister.gridy = 12;
		frmAppchatregister.getContentPane().add(btnRegister, gbc_btnRegister);
		
		btnIveAnAccount = new JButton("I've an account");
		btnIveAnAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			//TODO cambiar a ventana de login
				controlador.changeToLogin();
			}
		});
		GridBagConstraints gbc_btnIveAnAccount = new GridBagConstraints();
		gbc_btnIveAnAccount.anchor = GridBagConstraints.WEST;
		gbc_btnIveAnAccount.insets = new Insets(0, 0, 5, 5);
		gbc_btnIveAnAccount.gridx = 4;
		gbc_btnIveAnAccount.gridy = 12;
		frmAppchatregister.getContentPane().add(btnIveAnAccount, gbc_btnIveAnAccount);
		
		lblCampoObligatorioError = new JLabel("mandatary fields *");
		lblCampoObligatorioError.setForeground(Color.RED);
		lblCampoObligatorioError.setLabelFor(frmAppchatregister);
		lblCampoObligatorioError.setToolTipText("");
		GridBagConstraints gbc_lblCampoObligatorioError = new GridBagConstraints();
		gbc_lblCampoObligatorioError.gridheight = 2;
		gbc_lblCampoObligatorioError.insets = new Insets(0, 0, 5, 5);
		gbc_lblCampoObligatorioError.gridx = 3;
		gbc_lblCampoObligatorioError.gridy = 13;
		frmAppchatregister.getContentPane().add(lblCampoObligatorioError, gbc_lblCampoObligatorioError);
		lblCampoObligatorioError.setVisible(false);
	}

	public void exit() {
		this.frmAppchatregister.dispose();	
	}

}
