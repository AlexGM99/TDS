package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Datos_Grupo extends JFrame {

	private static final long serialVersionUID = -276951443569803746L;
	private DefaultListModel<ContactoIndividual> listModelContactos;
	private JPanel contentPane;
	private ControladorVistaAppChat controlador;
	private List<ContactoIndividual> contactos;
	private ContactoIndividual admin;
	private String nombre;
	private JList<ContactoIndividual> list;
	private JScrollPane scrollPane;
	/**
	 * Create the frame.
	 */
	public Datos_Grupo(ViewModelGrupo model) {
		this.controlador = model.getControlador();
		this.contactos = model.getContactos();
		this.admin = model.getAdmin();
		this.nombre = model.getNombre();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 449, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		listModelContactos = new DefaultListModel<ContactoIndividual>();

		list = new JList<ContactoIndividual>();
		scrollPane.setViewportView(list);
		
		JLabel label_1 = new JLabel("");
		scrollPane.setColumnHeaderView(label_1);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{144, 134, 0};
		gbl_panel.rowHeights = new int[]{36, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel labelNombre = new JLabel(nombre);
		GridBagConstraints gbc_labelNombre = new GridBagConstraints();
		gbc_labelNombre.insets = new Insets(0, 0, 5, 0);
		gbc_labelNombre.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelNombre.gridx = 1;
		gbc_labelNombre.gridy = 0;
		panel.add(labelNombre, gbc_labelNombre);
		labelNombre.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		labelNombre.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labelAdmin = new JLabel("");
		
		labelAdmin.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		String ruta = controlador.getImage(admin);
    	if (!ruta.trim().isEmpty()) {
    		File f = new File(ruta);
    		BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(f);
        		Image aux=myPicture.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        		ImageIcon aux1 = new ImageIcon(aux);
        		labelAdmin.setIcon(aux1);
			} catch (IOException e) {
				Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
        		ImageIcon img6=new ImageIcon(img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        		labelAdmin.setIcon(img6);
			}	
    	} else {
    		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
    		ImageIcon img6=new ImageIcon(img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    		labelAdmin.setIcon(img6);
    	}
    	labelAdmin.setText(admin.getNombre());
		
		
		GridBagConstraints gbc_labelAdmin = new GridBagConstraints();
		gbc_labelAdmin.gridx = 1;
		gbc_labelAdmin.gridy = 2;
		panel.add(labelAdmin, gbc_labelAdmin);
		
		setChats(new LinkedList<ContactoIndividual>(contactos));
	}

	public void visible(boolean i) {
		 setVisible(i);
	 }
	public void setChats(LinkedList<ContactoIndividual> listaModel) {
		listModelContactos = new DefaultListModel<ContactoIndividual>();
		listaModel.stream().forEach(cont -> listModelContactos.addElement(cont));
		list = new JList<ContactoIndividual>(listModelContactos);
		list.setMinimumSize(new Dimension(240, 0));
		scrollPane.setViewportView(list);
		list.setCellRenderer(new chatListRender());
	}
	
	private class chatListRender extends JLabel implements ListCellRenderer<ContactoIndividual> {

		private static final long serialVersionUID = -8675866247067660852L;

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
