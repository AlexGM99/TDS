package interfazGrafica;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.SoftBevelBorder;

import controlador.ControladorVistaAppChat;

import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

public class ChatWindow implements InterfazVistas{

	private JFrame frmAppchat;
	private JTextField txtBuscar;
	private JTextField txtChat;
	private JTextField textmensaje;
	
	private ControladorVistaAppChat controlador;

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
	public ChatWindow(ControladorVistaAppChat controlador) {
		initialize();
		this.controlador = controlador;
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
		
		JPanel panel = new JPanel();
		panel.setAutoscrolls(true);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("hola, aqui tienes los datos de individuo/grupo");
			}
		});
		frmAppchat.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1, 0, 0, 0, 21, 75, 165, 0, 35, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 28, 0, 37, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel mostrarPerfil = new JPanel();
		mostrarPerfil.setMaximumSize(new Dimension(50, 50));
		mostrarPerfil.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mostrarPerfil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO mostrar el perfil del usuario
				System.out.println("perfil");
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
		
		JLabel label_MifotoPerfil = new JLabel("");
		Image img= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/usuarioDefecto.png")).getImage();
		ImageIcon img2=new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		label_MifotoPerfil.setIcon(img2);
		GridBagConstraints gbc_label_MifotoPerfil = new GridBagConstraints();
		gbc_label_MifotoPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_label_MifotoPerfil.gridx = 0;
		gbc_label_MifotoPerfil.gridy = 0;
		mostrarPerfil.add(label_MifotoPerfil, gbc_label_MifotoPerfil);
		
		JLabel lblMiNombre = new JLabel("MI NOMBRE");
		GridBagConstraints gbc_lblMiNombre = new GridBagConstraints();
		gbc_lblMiNombre.insets = new Insets(0, 0, 0, 5);
		gbc_lblMiNombre.gridx = 2;
		gbc_lblMiNombre.gridy = 0;
		mostrarPerfil.add(lblMiNombre, gbc_lblMiNombre);
		
		JPanel nombreChat = new JPanel();
		nombreChat.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_nombreChat = new GridBagConstraints();
		gbc_nombreChat.gridheight = 2;
		gbc_nombreChat.insets = new Insets(0, 0, 5, 5);
		gbc_nombreChat.fill = GridBagConstraints.BOTH;
		gbc_nombreChat.gridx = 5;
		gbc_nombreChat.gridy = 0;
		panel.add(nombreChat, gbc_nombreChat);
		GridBagLayout gbl_nombreChat = new GridBagLayout();
		gbl_nombreChat.columnWidths = new int[]{0, 0, 0};
		gbl_nombreChat.rowHeights = new int[]{0, 0, 0};
		gbl_nombreChat.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_nombreChat.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		nombreChat.setLayout(gbl_nombreChat);
		
		JPanel panelimage = new JPanel();
		GridBagConstraints gbc_panelimage = new GridBagConstraints();
		gbc_panelimage.insets = new Insets(0, 0, 5, 5);
		gbc_panelimage.fill = GridBagConstraints.BOTH;
		gbc_panelimage.gridx = 0;
		gbc_panelimage.gridy = 0;
		nombreChat.add(panelimage, gbc_panelimage);
		
		JLabel lblImage = new JLabel("");
		panelimage.add(lblImage);
		
		JLabel lblnombrechat = new JLabel("");
		GridBagConstraints gbc_lblnombrechat = new GridBagConstraints();
		gbc_lblnombrechat.gridx = 1;
		gbc_lblnombrechat.gridy = 1;
		nombreChat.add(lblnombrechat, gbc_lblnombrechat);
		
		JPanel opciones_usuario = new JPanel();
		opciones_usuario.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		JPanel buscadorMensjs = new JPanel();
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
		
		JButton btnopciones = new JButton("options");
		btnopciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnopciones.setActionCommand("");
		opciones_usuario.add(btnopciones);
		
		final JPopupMenu popupMenu_1 = new JPopupMenu();
		addPopup(btnopciones, popupMenu_1);
		
		JButton btnDeleteContact = new JButton("Delete contact");
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
		
		JPanel panel_opciones_user = new JPanel();
		GridBagConstraints gbc_panel_opciones_user = new GridBagConstraints();
		gbc_panel_opciones_user.gridheight = 2;
		gbc_panel_opciones_user.insets = new Insets(0, 0, 5, 5);
		gbc_panel_opciones_user.fill = GridBagConstraints.VERTICAL;
		gbc_panel_opciones_user.gridx = 4;
		gbc_panel_opciones_user.gridy = 0;
		panel.add(panel_opciones_user, gbc_panel_opciones_user);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);
		
		JLabel label_1 = new JLabel("");
		Image img5= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/lupitaRed.png")).getImage();
		ImageIcon img6=new ImageIcon(img5.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		label_1.setIcon(img6);
		panel_1.add(label_1);
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
			public void keyTyped(KeyEvent e) {
				// TODO buscador por pulsación al igual que los mensajes
			}
		});
		txtChat.setText("chat");
		buscadorChats.add(txtChat);
		txtChat.setColumns(20);
		
		JScrollPane scrollChats = new JScrollPane();
		GridBagConstraints gbc_scrollChats = new GridBagConstraints();
		gbc_scrollChats.gridheight = 2;
		gbc_scrollChats.gridwidth = 4;
		gbc_scrollChats.insets = new Insets(0, 0, 0, 5);
		gbc_scrollChats.fill = GridBagConstraints.BOTH;
		gbc_scrollChats.gridx = 1;
		gbc_scrollChats.gridy = 3;
		panel.add(scrollChats, gbc_scrollChats);
		
		JList chatslist = new JList();
		scrollChats.setViewportView(chatslist);
		
		JScrollPane scroll_chat = new JScrollPane();
		GridBagConstraints gbc_scroll_chat = new GridBagConstraints();
		gbc_scroll_chat.gridheight = 2;
		gbc_scroll_chat.gridwidth = 5;
		gbc_scroll_chat.insets = new Insets(0, 0, 5, 0);
		gbc_scroll_chat.fill = GridBagConstraints.BOTH;
		gbc_scroll_chat.gridx = 5;
		gbc_scroll_chat.gridy = 2;
		panel.add(scroll_chat, gbc_scroll_chat);
		
		JList chat_list = new JList();
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
		
		JLabel label = new JLabel("");
		Image img3= new ImageIcon(ChatWindow.class.getResource("/ImagensDefault/envioAdap.png")).getImage();
		ImageIcon img4=new ImageIcon(img3.getScaledInstance(50, 27, Image.SCALE_SMOOTH));
		label.setIcon(img4);
		enviarMensaje.add(label, BorderLayout.CENTER);
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
}
