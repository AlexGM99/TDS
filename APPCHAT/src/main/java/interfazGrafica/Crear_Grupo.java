package interfazGrafica;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import controlador.ControladorVistaAppChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Usuario;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EtchedBorder;

public class Crear_Grupo extends JFrame {

	private DefaultListModel<ContactoIndividual> listModel;
	private List<ContactoIndividual> contactos;
	private String nombre_grupo;
	private ControladorVistaAppChat controlador;
	private JTextField Barra_de_búsqueda;
	private static int TAMAÑO_FLECHAS = 75;
	private JTextField txtNombreGrupo;
	private int codigoActivo = -1;
	private String actNick = "";
	/**
	 * Create the panel.
	 */
	public Crear_Grupo(Usuario usuarioActual,List<ContactoIndividual> contactos, ControladorVistaAppChat controlador) {
		this.contactos = contactos;
		this.controlador = controlador;
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 112, 137, 0, 344, 0};
		gbl_panel.rowHeights = new int[]{61, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		String foto = "/ImagensDefault/usuarioDefecto.png";
		if (usuarioActual.getImagen()!=null && !usuarioActual.getImagen().equals(""))
		{
			foto = usuarioActual.getImagen();
		}
		
		JLabel MiFoto =    new JLabel("");
		Image img= new ImageIcon(ChatWindow.class.getResource(foto)).getImage();
		ImageIcon img2=new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		MiFoto.setIcon(img2);
		GridBagConstraints gbc_MiFoto = new GridBagConstraints();
		gbc_MiFoto.insets = new Insets(0, 0, 0, 5);
		gbc_MiFoto.gridx = 0;
		gbc_MiFoto.gridy = 0;
		panel.add(MiFoto, gbc_MiFoto);
		
		JLabel MiNombre = new JLabel(usuarioActual.getNombre());
		GridBagConstraints gbc_MiNombre = new GridBagConstraints();
		gbc_MiNombre.insets = new Insets(0, 0, 0, 5);
		gbc_MiNombre.gridx = 1;
		gbc_MiNombre.gridy = 0;
		panel.add(MiNombre, gbc_MiNombre);
		
		JLabel buscador = new JLabel("");
		buscador.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Image img3= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/lupitaRed.png")).getImage();
		ImageIcon img4=new ImageIcon(img3.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		buscador.setIcon(img4);
		GridBagConstraints gbc_buscador = new GridBagConstraints();
		gbc_buscador.anchor = GridBagConstraints.EAST;
		gbc_buscador.insets = new Insets(0, 0, 0, 5);
		gbc_buscador.gridx = 3;
		gbc_buscador.gridy = 0;
		panel.add(buscador, gbc_buscador);
		
		Barra_de_búsqueda = new JTextField();
		GridBagConstraints gbc_Barra_de_búsqueda = new GridBagConstraints();
		gbc_Barra_de_búsqueda.fill = GridBagConstraints.HORIZONTAL;
		gbc_Barra_de_búsqueda.gridx = 4;
		gbc_Barra_de_búsqueda.gridy = 0;
		panel.add(Barra_de_búsqueda, gbc_Barra_de_búsqueda);
		Barra_de_búsqueda.setColumns(10);
		
		JScrollPane scrollPane_contacts = new JScrollPane();
		getContentPane().add(scrollPane_contacts, BorderLayout.WEST);
		
		JList list_contacts = new JList();
		scrollPane_contacts.setViewportView(list_contacts);
		
		listModel = new DefaultListModel<ContactoIndividual>();
		list_contacts = new JList<ContactoIndividual>(listModel);
		scrollPane_contacts.setViewportView(list_contacts);
		
		
		JScrollPane scrollPanel_seleccionados = new JScrollPane();
		getContentPane().add(scrollPanel_seleccionados, BorderLayout.EAST);
		
		JList list_seleccionados = new JList();
		scrollPanel_seleccionados.setViewportView(list_seleccionados);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JButton button = new JButton("");
		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/flecha-izquierda.png")).getImage();
		ImageIcon img6=new ImageIcon(img5.getScaledInstance(TAMAÑO_FLECHAS, TAMAÑO_FLECHAS, Image.SCALE_SMOOTH));
		panel_1.setLayout(new BorderLayout(0, 0));
		button.setIcon(img6);

		panel_1.add(button, BorderLayout.WEST);
		
		JButton button_1 = new JButton("");
		Image img7= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/proximo.png")).getImage();
		ImageIcon img8=new ImageIcon(img7.getScaledInstance(TAMAÑO_FLECHAS, TAMAÑO_FLECHAS, Image.SCALE_SMOOTH));
		button_1.setIcon(img8);
		panel_1.add(button_1, BorderLayout.EAST);
		
		txtNombreGrupo = new JTextField();
		txtNombreGrupo.setHorizontalAlignment(JTextField.CENTER);
		txtNombreGrupo.setText("Nombre Grupo");
		panel_1.add(txtNombreGrupo, BorderLayout.NORTH);
		txtNombreGrupo.setColumns(10);
		
		JButton btnCrearGrupo = new JButton("Crear Grupo");
		panel_1.add(btnCrearGrupo, BorderLayout.SOUTH);
		
	}
	
	public void addChat(ContactoIndividual cont) {
		
	}

	private class chatListRender extends JLabel implements ListCellRenderer<Contacto> {
		public chatListRender() {
		    setOpaque(true);
		}
	    public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto cont, int index,
	        boolean isSelected, boolean cellHasFocus) {
	    	this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    		String ruta = controlador.getImage((ContactoIndividual)cont);
	        	if (!ruta.trim().isEmpty()) {
	        		File f = new File(ruta);
	        		BufferedImage myPicture = null;
					try {
						myPicture = ImageIO.read(f);
					} catch (IOException e) {
						//TODO ADVERTENCIA
						Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
		        		ImageIcon img6=new ImageIcon(img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		        		this.setIcon(img6);
					}			
	        		Image aux=myPicture.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	        		ImageIcon aux1 = new ImageIcon(aux);
	        		this.setIcon(aux1);
	        	} else {
	        		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
	        		ImageIcon img6=new ImageIcon(img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	        		this.setIcon(img6);
	        	}
	        	this.setText(cont.getNombre());
	    	if (isSelected) {
	    	    setBackground(list.getSelectionBackground());
	    	    setForeground(list.getSelectionForeground());
	    	    codigoActivo = controlador.getCode((ContactoIndividual)cont);
	    	    actNick = cont.getNombre();
	    	} else {
	    	    setBackground(list.getBackground());
	    	    setForeground(list.getForeground());
	    	}
	        return this;
	    }
	}
	
}
