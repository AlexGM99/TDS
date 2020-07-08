package interfazGrafica;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.List;

import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Descuentos.DescuentoCompuesto;
import Descuentos.DescuentoSimple;
import ViewModels.ViewModelDatosChat;
import ViewModels.ViewModelUsuario;
import cargadorMensajes.SimpleTextParser;
import controlador.ControladorVistaAppChat;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Usuario;

import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import java.awt.ComponentOrientation;

public class ChatWindow implements InterfazVistas{

	private Crear_Grupo crearGrupo;
	
	private JFrame frmAppchat;
	private JTextField txtBuscar;
	private JTextField txtChat;
	private JTextField textmensaje;
	
	private JLabel lblMiNombre;
	private JPanel panel;
	private JPanel mostrarPerfil;
	private JLabel label_MifotoPerfil;
	private JPanel nombreChat;
	private JLabel lblnombrechat;
	private JPanel opciones_usuario;
	private JPanel buscadorMensjs;
	private JButton btnopciones;
	private JPanel panel_opciones_user;
	private JScrollPane scrollChats;
	private JList<Contacto> chatslist;
	private JScrollPane scroll_chat;
	private JList chat_list;
	private DefaultListModel<Contacto> listModel;
	
	private int codigoActivo = -1;
	private String actNick = "";
	
	private ControladorVistaAppChat controlador;
	private JPopupMenu popupMenu_2;
	private JButton btnChangePhoto;
	private JButton btnChangeGreeting;
	private JButton btnPremium;
	private JButton btnInfoUso;
	private JButton btnCargador;
	private JButton btnExit;
	private JButton btnNewContact;
	private JButton btnNewGroup;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatWindow window = new ChatWindow();
					window.frmAppchat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public ChatWindow(ControladorVistaAppChat controlador, Usuario user) {
		this.controlador = controlador;
		initialize();
		lblMiNombre.setText(user.getUsuario());
		if (!user.getImagen().trim().isEmpty()) {
		 Scanner entrada = null;
		 try {
	            String ruta = user.getImagen();
	            File f = new File(ruta);
	            entrada = new Scanner(f);
	            while (entrada.hasNext()) {
	                System.out.println(entrada.nextLine());
	            }
	            BufferedImage myPicture;
	            try { 
	    			myPicture = ImageIO.read(f);			
	    			Image aux=myPicture.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	    			label_MifotoPerfil.setIcon(new ImageIcon(aux));
	    			label_MifotoPerfil.repaint();
	    		} catch (IOException e1) {
	    			//e1.printStackTrace();
	    			JOptionPane.showConfirmDialog(chat_list, "Ha habido un error al cargar la imagen, "
	    					+ "la moviste de sitio :(", "error al recuperar la imagen", JOptionPane.WARNING_MESSAGE);
	    		}
	        } catch (FileNotFoundException e0) {
	            //System.out.println(e0.getMessage());
	        	JOptionPane.showConfirmDialog(chat_list, "Ha habido un error al cargar la imagen, "
    					+ "la moviste de sitio :(", "error al recuperar la imagen", JOptionPane.WARNING_MESSAGE);
	        } catch (NullPointerException e1) {
	            //System.out.println("No se ha seleccionado ningún fichero");
	        } catch (Exception e2) {
	            //System.out.println(e2.getMessage());
	        	JOptionPane.showConfirmDialog(chat_list, "Ha habido un error al cargar la imagen, "
    					+ "la moviste de sitio :(", "error al recuperar la imagen", JOptionPane.WARNING_MESSAGE);
	        } finally {
	            if (entrada != null) {
	                entrada.close();
	            }
	        }
		}
		this.frmAppchat.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAppchat = new JFrame();
		frmAppchat.setTitle("APPCHAT");
		frmAppchat.setBounds(100, 100, 450, 300);
		frmAppchat.setMinimumSize(new Dimension(700, 700));
		frmAppchat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAppchat.setLocationRelativeTo(null);
		frmAppchat.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setAutoscrolls(true);
		frmAppchat.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1, 0, 0, 0, 21, 75, 165, 0, 35, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 28, 0, 37, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		mostrarPerfil = new JPanel();
		mostrarPerfil.setMaximumSize(new Dimension(50, 50));
		mostrarPerfil.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mostrarPerfil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TO DO mostrar el perfil del usuario
					popupMenu_2.setLocation(mostrarPerfil.getLocationOnScreen());
					popupMenu_2.setVisible(!popupMenu_2.isVisible());
			}
		});
			
		GridBagConstraints gbc_mostrarPerfil = new GridBagConstraints();
		gbc_mostrarPerfil.gridheight = 2;
		gbc_mostrarPerfil.gridwidth = 3;
		gbc_mostrarPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_mostrarPerfil.fill = GridBagConstraints.BOTH;
		gbc_mostrarPerfil.gridx = 1;
		gbc_mostrarPerfil.gridy = 0;
		panel.add(mostrarPerfil, gbc_mostrarPerfil);
		GridBagLayout gbl_mostrarPerfil = new GridBagLayout();
		gbl_mostrarPerfil.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_mostrarPerfil.rowHeights = new int[]{0, 0};
		gbl_mostrarPerfil.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_mostrarPerfil.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		mostrarPerfil.setLayout(gbl_mostrarPerfil);
		
		label_MifotoPerfil =    new JLabel("");
		Image img= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
		ImageIcon img2=new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		label_MifotoPerfil.setIcon(img2);
		
		popupMenu_2 = new JPopupMenu();
		addPopup(mostrarPerfil, popupMenu_2);
		
		btnChangePhoto = new JButton("Change photo");
		btnChangePhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//TO DO cambiar la foto de perfil por la elegida por el usuario -> copiar de register + 
				Scanner entrada = null;
				popupMenu_2.setVisible(false);
		        JFileChooser fileChooser = new JFileChooser();
		        FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png"); 
		        fileChooser.setFileFilter(imgFilter);
		        fileChooser.showOpenDialog(fileChooser);
		        String ruta = "";
		        try {
		            ruta = fileChooser.getSelectedFile().getAbsolutePath();
		            File f = new File(ruta);
		            entrada = new Scanner(f);
		            while (entrada.hasNext()) {
		                System.out.println(entrada.nextLine());
		            }
		            BufferedImage myPicture;
		            try { 
		    			myPicture = ImageIO.read(f);			
		    			Image aux=myPicture.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		    			label_MifotoPerfil.setIcon(new ImageIcon(aux));
		    			label_MifotoPerfil.repaint();
		    			//TO DO llamar al controlador que actualice el usuario y analizar error
		    			boolean changed = controlador.changePhoto(ruta);
				        if (!changed) {
				        	JOptionPane.showConfirmDialog(chat_list, "There where an error in the process to become a dark boss with an appropiate image",
				        			"Couldn't change the image", JOptionPane.ERROR_MESSAGE);
				        }
		    		} catch (IOException e1) {
		    			//e1.printStackTrace();
		    			JOptionPane.showConfirmDialog(chat_list, "Ha habido un error al cargar la imagen, "
		    					+ "la moviste de sitio :(", "error al recuperar la imagen", JOptionPane.WARNING_MESSAGE);
		    		}
		        } catch (FileNotFoundException e0) {
		            //System.out.println(e0.getMessage());
		        	JOptionPane.showConfirmDialog(chat_list, "Ha habido un error al cargar la imagen, "
	    					+ "la moviste de sitio :(", "error al recuperar la imagen", JOptionPane.WARNING_MESSAGE);
		        } catch (NullPointerException e1) {
		            //System.out.println("No se ha seleccionado ningún fichero");
		        } catch (Exception e2) {
		            //System.out.println(e2.getMessage());
		        	JOptionPane.showConfirmDialog(chat_list, "Ha habido un error al cargar la imagen, "
	    					+ "la moviste de sitio :(", "error al recuperar la imagen", JOptionPane.WARNING_MESSAGE);
		        } finally {
		            if (entrada != null) {
		                entrada.close();
		            }
		        }
			}
		});
		popupMenu_2.add(btnChangePhoto);
		
		btnChangeGreeting = new JButton("change greeting");
		btnChangeGreeting.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TO DO cambiar el saludo y llamar al controlador que lo cambie en la BBDD 
				popupMenu_2.setVisible(false);
				String newGreeting = JOptionPane.showInputDialog(btnChangeGreeting, 
						"Your actual greeting: " + controlador.getGreeting(),
						"Change your greeting to please our lord!",
						JOptionPane.PLAIN_MESSAGE); 
				if (newGreeting !=null)
					controlador.changeGreeting(newGreeting);
			}
		});
		popupMenu_2.add(btnChangeGreeting);
		
		btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenu_2.setVisible(false);
				controlador.cerrarSesion();
			}
		});
		
		btnNewContact = new JButton("New Contact");
		btnNewContact.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenu_2.setVisible(false);
				String telefono = 
						JOptionPane.showInputDialog(null, "Introduce the phone number of the new contact", "New Contact", JOptionPane.QUESTION_MESSAGE);
				if (telefono != null && !controlador.existeUsuario(telefono)) {
					JOptionPane.showMessageDialog(null, "We couldn't find the number " + telefono);
				}else if (telefono != null){
					String nick =
							JOptionPane.showInputDialog(null, "Introduce how you would like to save it", "New Contact " + telefono, JOptionPane.QUESTION_MESSAGE);
					if (nick != null) {
						controlador.registrarContacto(nick, telefono);
					} else {
						JOptionPane.showMessageDialog(null, "this is not a name!");
					}
				}
				
			}
		});
		popupMenu_2.add(btnNewContact);
		
		btnNewGroup = new JButton("New group");
		btnNewGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				popupMenu_2.setVisible(false);
				crearGrupo = new Crear_Grupo(controlador.getUsuarioActual() ,controlador.getContactoIndividuales(), controlador);
				crearGrupo.setVisible(true);
			}
		});
		popupMenu_2.add(btnNewGroup);
		popupMenu_2.add(btnExit);
		
		if (!controlador.soypremium()) {
			btnPremium = new JButton("Ascenso a premium");
			btnPremium.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					DescuentoSimple descuentos = controlador.getMejorDescuento();
					popupMenu_2.setVisible(false);
					
					int opt = JOptionPane.showConfirmDialog(btnPremium, descuentos!=null?descuentos.getLetraPequena()+" - Descuento "+descuentos.getCantidad()+"% de tu alma " :"Vender tu alma completa", 
							descuentos!=null?"Unirse a la orden premium" + " - promoción: "+descuentos.getName():"Unirse a la orden premium", 
							JOptionPane.OK_OPTION);
					if (opt==JOptionPane.OK_OPTION) {
						controlador.vendoMiAlmaPorPremium();
						popupMenu_2.remove(btnPremium);
					}
				}
			});
			popupMenu_2.add(btnPremium);
		}
		
		else {
			btnInfoUso = new JButton("Show use info");
			btnInfoUso.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					popupMenu_2.setVisible(false);
					InfoUso infoUso = new InfoUso(controlador);
					infoUso.setVisible(true);
				}
			});
			popupMenu_2.add(btnInfoUso);
		}
		
		btnCargador = new JButton("Load messages");
		btnCargador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenu_2.setVisible(false);
				if ( !controlador.cargarMensajes("/home/edupema/Escritorio/cargar.txt", SimpleTextParser.FORMAT_DATE_IOS))
					JOptionPane.showMessageDialog(null, "There was an error loading your messages", "Boom!", JOptionPane.ERROR_MESSAGE);
				for (ContactoIndividual it : controlador.getUsuarioActual().getContactos()) {
					System.out.println(it.getMensajes());
				}
			}
		});
		popupMenu_2.add(btnCargador);
			
		GridBagConstraints gbc_label_MifotoPerfil = new GridBagConstraints();
		gbc_label_MifotoPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_label_MifotoPerfil.gridx = 0;
		gbc_label_MifotoPerfil.gridy = 0;
		mostrarPerfil.add(label_MifotoPerfil, gbc_label_MifotoPerfil);
		
		lblMiNombre = new JLabel("MI NOMBRE");
		GridBagConstraints gbc_lblMiNombre = new GridBagConstraints();
		gbc_lblMiNombre.insets = new Insets(0, 0, 0, 5);
		gbc_lblMiNombre.gridx = 2;
		gbc_lblMiNombre.gridy = 0;
		mostrarPerfil.add(lblMiNombre, gbc_lblMiNombre);
		
		nombreChat = new JPanel();
		nombreChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Mostrar la info del usuario del chat actual
				if (codigoActivo == -1) JOptionPane.showMessageDialog(chatslist, "U aren't in contact with no one know, select a chat to see the details of your ally",
													"You are not alone on the dark!", JOptionPane.WARNING_MESSAGE);
				else {
					ViewModelDatosChat informacion = controlador.getDatos(codigoActivo);
					if (informacion instanceof ViewModelUsuario)
					{
						Datos_Chat_Actual info = new Datos_Chat_Actual((ViewModelUsuario)informacion);
						info.setLocationRelativeTo(nombreChat);
						info.visible(true);
					}
				}
			}
		});
		nombreChat.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_nombreChat = new GridBagConstraints();
		gbc_nombreChat.gridheight = 2;
		gbc_nombreChat.insets = new Insets(0, 0, 5, 5);
		gbc_nombreChat.fill = GridBagConstraints.BOTH;
		gbc_nombreChat.gridx = 5;
		gbc_nombreChat.gridy = 0;
		panel.add(nombreChat, gbc_nombreChat);
		FlowLayout fl_nombreChat = new FlowLayout(FlowLayout.LEFT, 5, 5);
		fl_nombreChat.setAlignOnBaseline(true);
		nombreChat.setLayout(fl_nombreChat);
		
		lblnombrechat = new JLabel("");
		lblnombrechat.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		nombreChat.add(lblnombrechat);
		lblnombrechat.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		opciones_usuario = new JPanel();
		opciones_usuario.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		buscadorMensjs = new JPanel();
		buscadorMensjs.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_buscadorMensjs = new GridBagConstraints();
		gbc_buscadorMensjs.gridheight = 2;
		gbc_buscadorMensjs.insets = new Insets(0, 0, 5, 5);
		gbc_buscadorMensjs.fill = GridBagConstraints.BOTH;
		gbc_buscadorMensjs.gridx = 6;
		gbc_buscadorMensjs.gridy = 0;
		panel.add(buscadorMensjs, gbc_buscadorMensjs);
		
		txtBuscar = new JTextField();
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//TODO llamar a la función que busque mensaje
			}
		});
		txtBuscar.setText("buscar");
		buscadorMensjs.add(txtBuscar);
		txtBuscar.setColumns(10);
		GridBagConstraints gbc_opciones_usuario = new GridBagConstraints();
		gbc_opciones_usuario.gridwidth = 2;
		gbc_opciones_usuario.gridheight = 2;
		gbc_opciones_usuario.insets = new Insets(0, 0, 5, 5);
		gbc_opciones_usuario.fill = GridBagConstraints.BOTH;
		gbc_opciones_usuario.gridx = 7;
		gbc_opciones_usuario.gridy = 0;
		panel.add(opciones_usuario, gbc_opciones_usuario);
		
		btnopciones = new JButton("options");
		btnopciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnopciones.setActionCommand("");
		opciones_usuario.add(btnopciones);
		//botones de opciones de chat
		final JPopupMenu popupMenu_1 = new JPopupMenu();
		addPopup(btnopciones, popupMenu_1);
		
		JButton btnDeleteContact = new JButton("Delete contact");
		btnDeleteContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnDeleteContact.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO delete contact
			}
		});
		popupMenu_1.add(btnDeleteContact);
		
		JButton btnClearHistory = new JButton("Clear history");
		btnClearHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO clear history messages of the chat
			}
		});
		popupMenu_1.add(btnClearHistory);
		
		btnopciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 popupMenu_1.show(e.getComponent(), e.getX(), e.getY());
		         popupMenu_1.setVisible(true);
			}
		});
		
		panel_opciones_user = new JPanel();
		GridBagConstraints gbc_panel_opciones_user = new GridBagConstraints();
		gbc_panel_opciones_user.gridheight = 2;
		gbc_panel_opciones_user.insets = new Insets(0, 0, 5, 5);
		gbc_panel_opciones_user.fill = GridBagConstraints.VERTICAL;
		gbc_panel_opciones_user.gridx = 4;
		gbc_panel_opciones_user.gridy = 0;
		panel.add(panel_opciones_user, gbc_panel_opciones_user);
		
		JPanel panel_lupaDeco = new JPanel();
		GridBagConstraints gbc_panel_lupaDeco = new GridBagConstraints();
		gbc_panel_lupaDeco.gridwidth = 2;
		gbc_panel_lupaDeco.insets = new Insets(0, 0, 5, 5);
		gbc_panel_lupaDeco.fill = GridBagConstraints.BOTH;
		gbc_panel_lupaDeco.gridx = 1;
		gbc_panel_lupaDeco.gridy = 2;
		panel.add(panel_lupaDeco, gbc_panel_lupaDeco);
		
		JLabel label_lupaDeco = new JLabel("");
		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/lupitaRed.png")).getImage();
		ImageIcon img6=new ImageIcon(img5.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		label_lupaDeco.setIcon(img6);
		panel_lupaDeco.add(label_lupaDeco);
		JPanel buscadorChats = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buscadorChats.getLayout();
		flowLayout.setAlignOnBaseline(true);
		GridBagConstraints gbc_buscadorChats = new GridBagConstraints();
		gbc_buscadorChats.anchor = GridBagConstraints.WEST;
		gbc_buscadorChats.insets = new Insets(0, 0, 5, 5);
		gbc_buscadorChats.fill = GridBagConstraints.VERTICAL;
		gbc_buscadorChats.gridx = 3;
		gbc_buscadorChats.gridy = 2;
		panel.add(buscadorChats, gbc_buscadorChats);
		
		txtChat = new JTextField();
		txtChat.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtChat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TO DO buscador por pulsación al igual que los mensajes
				setChats((LinkedList<Contacto>)controlador.buscarChats(txtChat.getText()));
			}
		});
		txtChat.setText("chat");
		buscadorChats.add(txtChat);
		txtChat.setColumns(20);
		
		scrollChats = new JScrollPane();
		GridBagConstraints gbc_scrollChats = new GridBagConstraints();
		gbc_scrollChats.gridheight = 2;
		gbc_scrollChats.gridwidth = 4;
		gbc_scrollChats.insets = new Insets(0, 0, 0, 5);
		gbc_scrollChats.fill = GridBagConstraints.BOTH;
		gbc_scrollChats.gridx = 1;
		gbc_scrollChats.gridy = 3;
		panel.add(scrollChats, gbc_scrollChats);
		
		listModel = new DefaultListModel<Contacto>();
		chatslist = new JList<Contacto>(listModel);
		scrollChats.setViewportView(chatslist);
		
		scroll_chat = new JScrollPane();
		GridBagConstraints gbc_scroll_chat = new GridBagConstraints();
		gbc_scroll_chat.gridheight = 2;
		gbc_scroll_chat.gridwidth = 5;
		gbc_scroll_chat.insets = new Insets(0, 0, 5, 0);
		gbc_scroll_chat.fill = GridBagConstraints.BOTH;
		gbc_scroll_chat.gridx = 5;
		gbc_scroll_chat.gridy = 2;
		panel.add(scroll_chat, gbc_scroll_chat);
		
		chat_list = new JList();
		scroll_chat.setViewportView(chat_list);
		
		JScrollPane scrollmensaje = new JScrollPane();
		GridBagConstraints gbc_scrollmensaje = new GridBagConstraints();
		gbc_scrollmensaje.gridwidth = 2;
		gbc_scrollmensaje.insets = new Insets(0, 0, 0, 5);
		gbc_scrollmensaje.fill = GridBagConstraints.BOTH;
		gbc_scrollmensaje.gridx = 5;
		gbc_scrollmensaje.gridy = 4;
		panel.add(scrollmensaje, gbc_scrollmensaje);
		
		textmensaje = new JTextField();
		textmensaje.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n' || e.getKeyChar() == '\r') {
					controlador.enviarMensaje(textmensaje.getText(), codigoActivo);
				}
			}
		});
		scrollmensaje.setViewportView(textmensaje);
		textmensaje.setColumns(10);
		
		JPanel panel_emoji = new JPanel();
		panel_emoji.setMaximumSize(new Dimension(20, 20));
		panel_emoji.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_emoji.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("emojis");
			}
		});
		GridBagConstraints gbc_panel_emoji = new GridBagConstraints();
		gbc_panel_emoji.insets = new Insets(0, 0, 0, 5);
		gbc_panel_emoji.fill = GridBagConstraints.BOTH;
		gbc_panel_emoji.gridx = 7;
		gbc_panel_emoji.gridy = 4;
		panel.add(panel_emoji, gbc_panel_emoji);
		panel_emoji.setLayout(new BorderLayout(0, 0));
		
		JLabel labelemoji = new JLabel("");
		labelemoji.setIcon(new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/emoji20x20.png")));
		panel_emoji.add(labelemoji, BorderLayout.CENTER);
		
		JPanel enviarMensaje = new JPanel();
		enviarMensaje.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		enviarMensaje.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO enviar mensajes
			}
		});
		GridBagConstraints gbc_enviarMensaje = new GridBagConstraints();
		gbc_enviarMensaje.insets = new Insets(0, 0, 0, 5);
		gbc_enviarMensaje.fill = GridBagConstraints.BOTH;
		gbc_enviarMensaje.gridx = 8;
		gbc_enviarMensaje.gridy = 4;
		panel.add(enviarMensaje, gbc_enviarMensaje);
		enviarMensaje.setLayout(new BorderLayout(0, 0));
		
		JLabel labelEnviarMensaje = new JLabel("");
		labelEnviarMensaje.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controlador.enviarMensaje(textmensaje.getText(), codigoActivo);
			}
		});
		Image img3= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/envioAdap.png")).getImage();
		ImageIcon img4=new ImageIcon(img3.getScaledInstance(50, 27, Image.SCALE_SMOOTH));
		labelEnviarMensaje.setIcon(img4);
		enviarMensaje.add(labelEnviarMensaje, BorderLayout.CENTER);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public void exit() {
		frmAppchat.dispose();
	}
	
	private class chatListRender extends JLabel implements ListCellRenderer<Contacto> {
		public chatListRender() {
		    setOpaque(true);
		}
	    public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto cont, int index,
	        boolean isSelected, boolean cellHasFocus) {
	    	this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    	if(cont instanceof ContactoIndividual) {
	    		String ruta = controlador.getImage((ContactoIndividual)cont);
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
	        	if (isSelected) {
		    	    setBackground(list.getSelectionBackground());
		    	    setForeground(list.getSelectionForeground());
		    	    codigoActivo = controlador.getCode((ContactoIndividual)cont);
		    	    actNick = cont.getNombre();
		    	    ponerChat();
		    	} else {
		    	    setBackground(list.getBackground());
		    	    setForeground(list.getForeground());
		    	}
	    	}
	    	else if (cont instanceof ContactoGrupo){
	    		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/Grupo.png")).getImage();
        		ImageIcon img6=new ImageIcon(img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        		this.setIcon(img6);
        		if (isSelected) {
    	    	    setBackground(list.getSelectionBackground());
    	    	    setForeground(list.getSelectionForeground());
    	    	    codigoActivo = ((ContactoGrupo)cont).getCodigo();
    	    	    actNick = cont.getNombre();
    	    	    ponerChat();
    	    	} else {
    	    	    setBackground(list.getBackground());
    	    	    setForeground(list.getForeground());
    	    	}
        		this.setText(cont.getNombre());
	        	if (isSelected) {
		    	    setBackground(list.getSelectionBackground());
		    	    setForeground(list.getSelectionForeground());
		    	    codigoActivo = ((ContactoGrupo)cont).getCodigo();
		    	    actNick = cont.getNombre();
		    	    ponerChat();
		    	} else {
		    	    setBackground(list.getBackground());
		    	    setForeground(list.getForeground());
		    	}
	    	}
	    	
	        return this;
	    }
	}
	
	private void ponerChat() {
		String ruta = controlador.getImage(codigoActivo);
		if(ruta.equals("GRUPO"))
		{
			Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/Grupo.png")).getImage();
    		ImageIcon img6=new ImageIcon(img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    		lblnombrechat.setIcon(img6);
		}
		else if (!ruta.trim().isEmpty()) {
    		File f = new File(ruta);
    		BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(f);
			} catch (IOException e) {
				//TODO ADVERTENCIA
			}			
    		Image aux=myPicture.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    		lblnombrechat.setIcon(new ImageIcon(aux));
    	} else {
    		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
    		ImageIcon img6=new ImageIcon(img5.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    		lblnombrechat.setIcon(img6);
    	}
    	lblnombrechat.setText(actNick);
	}
	
	public void setChats(LinkedList<Contacto> listaModel) {
		listModel = new DefaultListModel<Contacto>();
		listaModel.stream().forEach(cont -> listModel.addElement(cont));
		chatslist = new JList<Contacto>(listModel);
		scrollChats.setViewportView(chatslist);
		chatslist.setCellRenderer(new chatListRender());
	}
	public void addChat(Contacto cont) {
		listModel.addElement(cont);
		chatslist = new JList<Contacto>(listModel);
		scrollChats.setViewportView(chatslist);
		chatslist.setCellRenderer(new chatListRender());
	}
	
}
