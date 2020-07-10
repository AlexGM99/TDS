package interfazGrafica;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.toedter.calendar.JDateChooser;

import controlador.ControladorVistaAppChat;

import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;

public class PanelBuscador extends JFrame {

	private static final long serialVersionUID = 1514085002165256306L;
	private JTextField textField_usuario;
	private JTextField textField_texto;

	/**
	 * Create the panel.
	 */
	public PanelBuscador(int codigoActivo, ControladorVistaAppChat contolador) {
		setMinimumSize(new Dimension(500, 500));
		setSize(new Dimension(500, 500));
		getContentPane().setSize(new Dimension(500, 500));
		getContentPane().setPreferredSize(new Dimension(500, 500));
		getContentPane().setMinimumSize(new Dimension(500, 500));
		this.setLocationRelativeTo(null);
		setPreferredSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		JLabel lblNewLabel = new JLabel("Usuario:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		textField_usuario = new JTextField();
		GridBagConstraints gbc_textField_usuario = new GridBagConstraints();
		gbc_textField_usuario.insets = new Insets(0, 0, 5, 5);
		gbc_textField_usuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_usuario.gridx = 3;
		gbc_textField_usuario.gridy = 1;
		getContentPane().add(textField_usuario, gbc_textField_usuario);
		textField_usuario.setColumns(10);
		
		JLabel lblFechaInicio = new JLabel("Fecha inicio:");
		GridBagConstraints gbc_lblFechaInicio = new GridBagConstraints();
		gbc_lblFechaInicio.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaInicio.gridx = 2;
		gbc_lblFechaInicio.gridy = 2;
		getContentPane().add(lblFechaInicio, gbc_lblFechaInicio);
		
		JDateChooser dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 3;
		gbc_dateChooser.gridy = 2;
		getContentPane().add(dateChooser, gbc_dateChooser);
		
		JLabel lblFechaFin = new JLabel("Fecha fin:");
		GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
		gbc_lblFechaFin.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaFin.gridx = 2;
		gbc_lblFechaFin.gridy = 3;
		getContentPane().add(lblFechaFin, gbc_lblFechaFin);
		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/lupitaRed.png")).getImage();
		ImageIcon img6=new ImageIcon(img5.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		JDateChooser dateChooser_1 = new JDateChooser();
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.fill = GridBagConstraints.BOTH;
		gbc_dateChooser_1.gridx = 3;
		gbc_dateChooser_1.gridy = 3;
		getContentPane().add(dateChooser_1, gbc_dateChooser_1);
		
		JLabel lblUsuario = new JLabel("mensaje");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 2;
		gbc_lblUsuario.gridy = 4;
		getContentPane().add(lblUsuario, gbc_lblUsuario);
		
		textField_texto = new JTextField();
		GridBagConstraints gbc_textField_texto = new GridBagConstraints();
		gbc_textField_texto.insets = new Insets(0, 0, 5, 5);
		gbc_textField_texto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_texto.gridx = 3;
		gbc_textField_texto.gridy = 4;
		getContentPane().add(textField_texto, gbc_textField_texto);
		textField_texto.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String usuario = textField_usuario.getText();
				Date inicio = dateChooser.getDate();
				Date fin = dateChooser_1.getDate();;
				String texto = textField_texto.getText();
				contolador.buscarMensajeContacto(usuario, inicio, fin, texto, codigoActivo);
				exit();
			}
		});
		lblNewLabel_1.setIcon(img6);
		
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 5;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

	}
	
	public void visible(boolean i) {
		 setVisible(i);
	 }

	public void exit() {
		this.dispose();
	}
}
