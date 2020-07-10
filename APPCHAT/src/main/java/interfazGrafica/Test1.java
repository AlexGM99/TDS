package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tds.BubbleText;
import javax.swing.JScrollPane;

public class Test1 {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Test1();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
				
		JFrame bombilla = new JFrame();
		bombilla.setLocationRelativeTo(null);
		bombilla.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		bombilla.setBounds(100, 100, 328, 119);
		JPanel panel1 = new JPanel();
		panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
		bombilla.setContentPane(panel1);
		panel1.setLayout(new BorderLayout(0, 0));
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = bombilla.getSize();
        bombilla.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		
		JPanel panel2 = new JPanel();
		panel1.add(panel2, BorderLayout.SOUTH);
		
		JButton btnIos = new JButton("iOS");
		btnIos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("1");
			}
		});
		panel2.add(btnIos);
		
		JScrollPane scrollPane = new JScrollPane();
		//panel1.add(scrollPane, BorderLayout.EAST);
		
		JScrollPane scroll_chat = new JScrollPane();
		GridBagConstraints gbc_scroll_chat = new GridBagConstraints();
		gbc_scroll_chat.gridheight = 2;
		gbc_scroll_chat.gridwidth = 5;
		gbc_scroll_chat.insets = new Insets(0, 0, 5, 0);
		gbc_scroll_chat.fill = GridBagConstraints.BOTH;
		gbc_scroll_chat.gridx = 5;
		gbc_scroll_chat.gridy = 2;
		panel1.add(scroll_chat, BorderLayout.EAST);
		
		JList<JButton>chat_list = new JList<JButton>();
		
		
		for (int i = 0; i < BubbleText.MAXICONO; i++) {
			JButton boton = new JButton();
			Icon icono = BubbleText.getEmoji(i);
			boton.setIcon(icono);
			boton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					bombilla.setVisible(false);
					//System.out.println(icono);
					JFrame aux1 = new JFrame();
					aux1.setMinimumSize(new Dimension(100, 100));
					JLabel aux2 = new JLabel();
					aux2.setIcon(icono);
					aux1.getContentPane().add(aux2);
					aux1.setVisible(true);
				}
			});
			chat_list.add(boton);
			//panel2.add(boton);
		}
		
		/*
		JButton btnAndroid1 = new JButton("Android 1");
		btnAndroid1.setIcon(BubbleText.getEmoji(1));
		btnAndroid1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bombilla.setVisible(false);
				System.out.println("2");
			}
		});
		panel2.add(btnAndroid1);
		JButton btnAndroid2 = new JButton("Android 2");
		btnAndroid2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bombilla.setVisible(false);
				System.out.println("3");
			}
		});
		panel2.add(btnAndroid2);
		
		JPanel panel3 = new JPanel();
		panel1.add(panel3, BorderLayout.CENTER);
		panel3.setLayout(new BorderLayout(0, 0));
		JLabel lblNewLabel = new JLabel("Choose the date format");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel3.add(lblNewLabel);*/
		scroll_chat.setViewportView(chat_list);
		bombilla.setVisible(true);
	}

}
