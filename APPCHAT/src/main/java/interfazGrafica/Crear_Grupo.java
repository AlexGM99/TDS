package interfazGrafica;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
import java.io.FileNotFoundException;
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
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Crear_Grupo extends JFrame implements InterfazVistas {

	private JScrollPane scrollPane_contacts;
	private JList list_contacts;
	private DefaultListModel<ContactoIndividual> listModelContactos;
	private DefaultListModel<ContactoIndividual> listModelSeleccionados;
	private List<ContactoIndividual> contactos;
	private String nombre_grupo;
	private ControladorVistaAppChat controlador;
	private JTextField Barra_de_búsqueda;
	private static int TAMAÑO_FLECHAS = 75;
	private JTextField txtNombreGrupo;
	private int codigoActivo = -1;
	private String actNick = "";
	private LinkedList<Integer> seleccionados;
	private LinkedList<Integer> contactosUsuario;
	private JScrollPane scrollPanel_seleccionados;
	private JList list_seleccionados;
	/**
	 * Create the panel.
	 */
	public Crear_Grupo(Usuario usuarioActual, List<ContactoIndividual> contactos, ControladorVistaAppChat controlador) {
		contactosUsuario = new LinkedList<Integer>();
		contactos.stream().forEach(p -> contactosUsuario.add(p.getCodigo()));
		seleccionados = new LinkedList<Integer>();
		setMaximumSize(new Dimension(760, 400));
		setResizable(false);
		setMinimumSize(new Dimension(760, 400));
		setLocationRelativeTo(null);
		this.contactos = contactos;
		this.controlador = controlador;
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 112, 137, 0, 344, 0};
		gbl_panel.rowHeights = new int[]{63, -16, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		JLabel buscador = new JLabel("");
		buscador.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Image img3= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/lupitaRed.png")).getImage();
		ImageIcon img4=new ImageIcon(img3.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		JPanel panel_usuario = new JPanel();
		panel_usuario.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panel_usuario = new GridBagConstraints();
		gbc_panel_usuario.gridwidth = 2;
		gbc_panel_usuario.insets = new Insets(0, 0, 5, 5);
		gbc_panel_usuario.fill = GridBagConstraints.BOTH;
		gbc_panel_usuario.gridx = 0;
		gbc_panel_usuario.gridy = 0;
		panel.add(panel_usuario, gbc_panel_usuario);
		GridBagLayout gbl_panel_usuario = new GridBagLayout();
		gbl_panel_usuario.columnWidths = new int[]{1, 55, 0};
		gbl_panel_usuario.rowHeights = new int[]{14, 0};
		gbl_panel_usuario.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_usuario.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_usuario.setLayout(gbl_panel_usuario);
		
		JLabel MiFoto = new JLabel("");
		String foto = "";
		if (usuarioActual.getImagen()!=null && !usuarioActual.getImagen().equals(""))
		{
			foto = usuarioActual.getImagen();
		}
		
		if (!usuarioActual.getImagen().trim().isEmpty()) {
			 Scanner entrada = null;
			 try {
		            String ruta = usuarioActual.getImagen();
		            File f = new File(ruta);
		            entrada = new Scanner(f);
		            while (entrada.hasNext()) {
		                System.out.println(entrada.nextLine());
		            }
		            BufferedImage myPicture;
		            try { 
		    			myPicture = ImageIO.read(f);			
		    			Image aux=myPicture.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		    			MiFoto.setIcon(new ImageIcon(aux));
		    			MiFoto.repaint();
		    		} catch (IOException e1) {
		    			//e1.printStackTrace();
		    			JOptionPane.showConfirmDialog(MiFoto, "Ha habido un error al cargar la imagen, "
		    					+ "la moviste de sitio :(", "error al recuperar la imagen", JOptionPane.WARNING_MESSAGE);
		    		}
		        } catch (FileNotFoundException e0) {
		            //System.out.println(e0.getMessage());
		        	JOptionPane.showConfirmDialog(MiFoto, "Ha habido un error al cargar la imagen, "
	    					+ "la moviste de sitio :(", "error al recuperar la imagen", JOptionPane.WARNING_MESSAGE);
		        } catch (NullPointerException e1) {
		            //System.out.println("No se ha seleccionado ningún fichero");
		        } catch (Exception e2) {
		            //System.out.println(e2.getMessage());
		        	JOptionPane.showConfirmDialog(MiFoto, "Ha habido un error al cargar la imagen, "
	    					+ "la moviste de sitio :(", "error al recuperar la imagen", JOptionPane.WARNING_MESSAGE);
		        } finally {
		            if (entrada != null) {
		                entrada.close();
		            }
		        }
			}
		else {
			foto = "/ImagensDefault/usuarioDefecto.png";
			Image img= new ImageIcon(ChatWindow.class.getResource(foto)).getImage();
			ImageIcon img2=new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			MiFoto.setIcon(img2);
		}
		GridBagConstraints gbc_MiFoto = new GridBagConstraints();
		gbc_MiFoto.anchor = GridBagConstraints.WEST;
		gbc_MiFoto.insets = new Insets(0, 0, 0, 5);
		gbc_MiFoto.gridx = 0;
		gbc_MiFoto.gridy = 0;
		panel_usuario.add(MiFoto, gbc_MiFoto);
		
		JLabel MiNombre = new JLabel(usuarioActual.getUsuario());
		GridBagConstraints gbc_MiNombre = new GridBagConstraints();
		gbc_MiNombre.anchor = GridBagConstraints.NORTHWEST;
		gbc_MiNombre.gridx = 1;
		gbc_MiNombre.gridy = 0;
		panel_usuario.add(MiNombre, gbc_MiNombre);
		buscador.setIcon(img4);
		GridBagConstraints gbc_buscador = new GridBagConstraints();
		gbc_buscador.anchor = GridBagConstraints.EAST;
		gbc_buscador.insets = new Insets(0, 0, 5, 5);
		gbc_buscador.gridx = 3;
		gbc_buscador.gridy = 0;
		panel.add(buscador, gbc_buscador);
		
		Barra_de_búsqueda = new JTextField();
		GridBagConstraints gbc_Barra_de_búsqueda = new GridBagConstraints();
		gbc_Barra_de_búsqueda.insets = new Insets(0, 0, 5, 0);
		gbc_Barra_de_búsqueda.fill = GridBagConstraints.HORIZONTAL;
		gbc_Barra_de_búsqueda.gridx = 4;
		gbc_Barra_de_búsqueda.gridy = 0;
		panel.add(Barra_de_búsqueda, gbc_Barra_de_búsqueda);
		Barra_de_búsqueda.setColumns(10);
		
		scrollPane_contacts = new JScrollPane();
		scrollPane_contacts.setAutoscrolls(true);
		scrollPane_contacts.setMinimumSize(new Dimension(265, 200));
		scrollPane_contacts.setPreferredSize(new Dimension(265, 200));
		getContentPane().add(scrollPane_contacts, BorderLayout.WEST);
		
		listModelContactos = new DefaultListModel<ContactoIndividual>();
		listModelSeleccionados = new DefaultListModel<ContactoIndividual>();
		
		JScrollPane scrollPanel_seleccionados = new JScrollPane();
		scrollPanel_seleccionados.setAutoscrolls(true);
		scrollPanel_seleccionados.setMinimumSize(new Dimension(260, 80));
		scrollPanel_seleccionados.setPreferredSize(new Dimension(260, 200));
		getContentPane().add(scrollPanel_seleccionados, BorderLayout.EAST);
		
		list_seleccionados = new JList();
		scrollPanel_seleccionados.setViewportView(list_seleccionados);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JButton button_quitarSeleccionado = new JButton("");
		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/flecha-izquierda.png")).getImage();
		ImageIcon img6=new ImageIcon(img5.getScaledInstance(TAMAÑO_FLECHAS, TAMAÑO_FLECHAS, Image.SCALE_SMOOTH));
		panel_1.setLayout(new BorderLayout(0, 0));
		button_quitarSeleccionado.setIcon(img6);

		panel_1.add(button_quitarSeleccionado, BorderLayout.WEST);
		
		JButton button_nuevoSeleccionado = new JButton("");
		button_nuevoSeleccionado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addChatSeleccionado();
			}
		});
		Image img7= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/proximo.png")).getImage();
		ImageIcon img8=new ImageIcon(img7.getScaledInstance(TAMAÑO_FLECHAS, TAMAÑO_FLECHAS, Image.SCALE_SMOOTH));
		button_nuevoSeleccionado.setIcon(img8);
		panel_1.add(button_nuevoSeleccionado, BorderLayout.EAST);
		
		txtNombreGrupo = new JTextField();
		txtNombreGrupo.setHorizontalAlignment(JTextField.CENTER);
		txtNombreGrupo.setText("Nombre Grupo");
		panel_1.add(txtNombreGrupo, BorderLayout.NORTH);
		txtNombreGrupo.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exit();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnCancelar.setPreferredSize(new Dimension(100, 23));
		btnCancelar.setMinimumSize(new Dimension(100, 23));
		btnCancelar.setMaximumSize(new Dimension(100, 23));
		panel_2.add(btnCancelar);
		
		JButton btnCrearGrupo = new JButton("Crear Grupo");
		btnCrearGrupo.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnCrearGrupo.setPreferredSize(new Dimension(100, 23));
		btnCrearGrupo.setMinimumSize(new Dimension(100, 23));
		btnCrearGrupo.setMaximumSize(new Dimension(100, 23));
		panel_2.add(btnCrearGrupo);
		
		
		setChats(new LinkedList<ContactoIndividual>(contactos));

	}
	
	public void addChatSeleccionado() {
		contactosUsuario.remove((Integer)codigoActivo);
		seleccionados.add((Integer)codigoActivo);
		LinkedList<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>(controlador.getContactosByCodigos(seleccionados));
		setChatsSeleccionados(contactos);
	}
	
	public void setChats(LinkedList<ContactoIndividual> listaModel) {
		listModelContactos = new DefaultListModel<ContactoIndividual>();
		listaModel.stream().forEach(cont -> listModelContactos.addElement(cont));
		list_contacts = new JList<ContactoIndividual>(listModelContactos);
		list_contacts.setMinimumSize(new Dimension(240, 0));
		scrollPane_contacts.setViewportView(list_contacts);
		list_contacts.setCellRenderer(new chatListRender());
	}
	public void setChatsSeleccionados(LinkedList<ContactoIndividual> listaModel) {
		listModelSeleccionados = new DefaultListModel<ContactoIndividual>();
		listaModel.stream().forEach(cont -> listModelSeleccionados.addElement(cont));
		list_seleccionados = new JList<ContactoIndividual>(listModelSeleccionados);
		list_seleccionados.setMinimumSize(new Dimension(240, 0));
		scrollPane_contacts.setViewportView(list_seleccionados);
		list_seleccionados.setCellRenderer(new chatListRender());
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
	    	    codigoActivo = cont.getCodigo();
	    	    actNick = cont.getNombre();
	    	} else {
	    	    setBackground(list.getBackground());
	    	    setForeground(list.getForeground());
	    	}
	        return this;
	    }
	}
	public void exit() {
		this.dispose();
	}
	
}
