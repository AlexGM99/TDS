package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;

public class Datos_Chat_Actual extends JFrame {

	private JPanel contentPane;
	JLabel labelFoto;
	JLabel lblNombre;
	JLabel lblTelefono;
	JLabel lblEstado;
	
	public Datos_Chat_Actual (String ruta, String nombre, String tel, String est) {
		this.Datos_Chat_ActualIni();
		if (!ruta.trim().isEmpty()) {
    		File f = new File(ruta);
    		BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(f);
			} catch (IOException e) {
				//TODO ADVERTENCIA
			}			
    		Image aux=myPicture.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    		ImageIcon aux1 = new ImageIcon(aux);
    		labelFoto.setIcon(aux1);
    	} else {
    		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
    		ImageIcon img6=new ImageIcon(img5.getScaledInstance(80, 80, Image.SCALE_SMOOTH));
    		labelFoto.setIcon(img6);
    	}
		lblNombre.setText(nombre);
		lblTelefono.setText(tel);
		lblEstado.setText(est);
		
	}
	 public void visible(boolean i) {
		 setVisible(i);
	 }

	/**
	 * Create the frame.
	 */
	public void Datos_Chat_ActualIni() {
		setTitle("APPCHAT");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 282, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{50, 0, 0, 0, 50, 0};
		gbl_contentPane.rowHeights = new int[]{50, 0, 0, 0, 0, 50, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		labelFoto = new JLabel("");
		GridBagConstraints gbc_labelFoto = new GridBagConstraints();
		gbc_labelFoto.insets = new Insets(0, 0, 5, 5);
		gbc_labelFoto.gridx = 2;
		gbc_labelFoto.gridy = 1;
		contentPane.add(labelFoto, gbc_labelFoto);
		
		lblNombre = new JLabel("nombre");
		lblNombre.setFont(new Font("Arial Black", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 2;
		gbc_lblNombre.gridy = 2;
		contentPane.add(lblNombre, gbc_lblNombre);
		
		lblTelefono = new JLabel("telefono:");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 2;
		gbc_lblTelefono.gridy = 3;
		contentPane.add(lblTelefono, gbc_lblTelefono);
		
		lblEstado = new JLabel("estado:");
		GridBagConstraints gbc_lblEstado = new GridBagConstraints();
		gbc_lblEstado.insets = new Insets(0, 0, 5, 5);
		gbc_lblEstado.gridx = 2;
		gbc_lblEstado.gridy = 4;
		contentPane.add(lblEstado, gbc_lblEstado);
	}

}
