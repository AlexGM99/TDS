package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import ViewModels.ViewModelGrupo;
import controlador.ControladorVistaAppChat;
import modelo.ContactoIndividual;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.JList;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Datos_Grupo extends JFrame {

	private JPanel contentPane;
	private ControladorVistaAppChat controlador;
	private List<ContactoIndividual> contactos;
	private ContactoIndividual admin;
	private String nombre;
	/**
	 * Create the frame.
	 */
	public Datos_Grupo(ViewModelGrupo model) {
		this.controlador = model.getControlador();
		this.contactos = model.getContactos();
		this.admin = model.getAdmin();
		this.nombre = model.getNombre();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel labelNombre = new JLabel(nombre);
		labelNombre.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		labelNombre.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelNombre, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JLabel label_1 = new JLabel("");
		scrollPane.setColumnHeaderView(label_1);
	}

	public void visible(boolean i) {
		 setVisible(i);
	 }
	
	private class chatListRender extends JLabel implements ListCellRenderer<ContactoIndividual> {
		public chatListRender() {
		    setOpaque(true);
		}
		
	    public Component getListCellRendererComponent(JList<? extends ContactoIndividual> list, ContactoIndividual cont, int index,
	        boolean isSelected, boolean cellHasFocus) {
	    	this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    		String ruta = controlador.getImage(cont);
	        	if (!ruta.trim().isEmpty()) {
	        		File f = new File(ruta);
	        		BufferedImage myPicture = null;
					try {
						myPicture = ImageIO.read(f);
		        		Image aux=myPicture.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		        		ImageIcon aux1 = new ImageIcon(aux);
		        		this.setIcon(aux1);
					} catch (IOException e) {
						//TODO ADVERTENCIA
						Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
		        		ImageIcon img6=new ImageIcon(img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		        		this.setIcon(img6);
					}	
	        	} else {
	        		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
	        		ImageIcon img6=new ImageIcon(img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	        		this.setIcon(img6);
	        	}
	        	this.setText(cont.getNombre());
	        return this;
	    }
	}
}
