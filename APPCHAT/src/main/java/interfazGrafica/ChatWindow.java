package interfazGrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import javax.swing.JSeparator;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Font;

public class ChatWindow {

	private JFrame frame;
	private JTextField txtBuscar;
	private JTextField txtChat;
	private JTextField textmensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatWindow window = new ChatWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setAutoscrolls(true);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("hola, aqui tienes los datos de individuo/grupo");
			}
		});
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1, 0, 0, 0, 0, 165, 35, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 28, 37, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel buscadorChats = new JPanel();
		GridBagConstraints gbc_buscadorChats = new GridBagConstraints();
		gbc_buscadorChats.gridheight = 2;
		gbc_buscadorChats.gridwidth = 3;
		gbc_buscadorChats.insets = new Insets(0, 0, 5, 5);
		gbc_buscadorChats.fill = GridBagConstraints.BOTH;
		gbc_buscadorChats.gridx = 1;
		gbc_buscadorChats.gridy = 0;
		panel.add(buscadorChats, gbc_buscadorChats);
		
		txtChat = new JTextField();
		txtChat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO buscador por pulsación al igual que los mensajes
			}
		});
		txtChat.setText("chat");
		buscadorChats.add(txtChat);
		txtChat.setColumns(10);
		
		JPanel nombreChat = new JPanel();
		nombreChat.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_nombreChat = new GridBagConstraints();
		gbc_nombreChat.gridheight = 2;
		gbc_nombreChat.insets = new Insets(0, 0, 5, 5);
		gbc_nombreChat.fill = GridBagConstraints.BOTH;
		gbc_nombreChat.gridx = 4;
		gbc_nombreChat.gridy = 0;
		panel.add(nombreChat, gbc_nombreChat);
		
		JPanel opciones_usuario = new JPanel();
		opciones_usuario.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		opciones_usuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("opciones preciosas");
			}
		});
		
		JPanel buscadorMensjs = new JPanel();
		buscadorMensjs.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_buscadorMensjs = new GridBagConstraints();
		gbc_buscadorMensjs.gridheight = 2;
		gbc_buscadorMensjs.insets = new Insets(0, 0, 5, 5);
		gbc_buscadorMensjs.fill = GridBagConstraints.BOTH;
		gbc_buscadorMensjs.gridx = 5;
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
		gbc_opciones_usuario.gridheight = 2;
		gbc_opciones_usuario.insets = new Insets(0, 0, 5, 5);
		gbc_opciones_usuario.fill = GridBagConstraints.BOTH;
		gbc_opciones_usuario.gridx = 6;
		gbc_opciones_usuario.gridy = 0;
		panel.add(opciones_usuario, gbc_opciones_usuario);
		
		JScrollPane scrollChats = new JScrollPane();
		GridBagConstraints gbc_scrollChats = new GridBagConstraints();
		gbc_scrollChats.gridheight = 2;
		gbc_scrollChats.gridwidth = 3;
		gbc_scrollChats.insets = new Insets(0, 0, 0, 5);
		gbc_scrollChats.fill = GridBagConstraints.BOTH;
		gbc_scrollChats.gridx = 1;
		gbc_scrollChats.gridy = 2;
		panel.add(scrollChats, gbc_scrollChats);
		
		JList chatslist = new JList();
		scrollChats.setViewportView(chatslist);
		
		JScrollPane scroll_chat = new JScrollPane();
		GridBagConstraints gbc_scroll_chat = new GridBagConstraints();
		gbc_scroll_chat.gridwidth = 3;
		gbc_scroll_chat.insets = new Insets(0, 0, 5, 5);
		gbc_scroll_chat.fill = GridBagConstraints.BOTH;
		gbc_scroll_chat.gridx = 4;
		gbc_scroll_chat.gridy = 2;
		panel.add(scroll_chat, gbc_scroll_chat);
		
		JList chat_list = new JList();
		scroll_chat.setViewportView(chat_list);
		
		JScrollPane scrollmensaje = new JScrollPane();
		GridBagConstraints gbc_scrollmensaje = new GridBagConstraints();
		gbc_scrollmensaje.gridwidth = 2;
		gbc_scrollmensaje.insets = new Insets(0, 0, 0, 5);
		gbc_scrollmensaje.fill = GridBagConstraints.BOTH;
		gbc_scrollmensaje.gridx = 4;
		gbc_scrollmensaje.gridy = 3;
		panel.add(scrollmensaje, gbc_scrollmensaje);
		
		textmensaje = new JTextField();
		scrollmensaje.setViewportView(textmensaje);
		textmensaje.setColumns(10);
		
		JPanel enviarMensaje = new JPanel();
		enviarMensaje.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO enviar mensajes
			}
		});
		GridBagConstraints gbc_enviarMensaje = new GridBagConstraints();
		gbc_enviarMensaje.insets = new Insets(0, 0, 0, 5);
		gbc_enviarMensaje.fill = GridBagConstraints.BOTH;
		gbc_enviarMensaje.gridx = 6;
		gbc_enviarMensaje.gridy = 3;
		panel.add(enviarMensaje, gbc_enviarMensaje);
	}
}
