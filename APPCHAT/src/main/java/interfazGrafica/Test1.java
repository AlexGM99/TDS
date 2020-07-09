package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DateTimeException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cargadorMensajes.SimpleTextParser;
import tds.BubbleText;

public class Test1 {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test1 window = new Test1();
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
				bombilla.setVisible(false);
				System.out.println("1");
			}
		});
		panel2.add(btnIos);
		
		
		for (int i = 0; i < BubbleText.MAXICONO; i++) {
			JButton boton = new JButton();
			boton.setIcon(BubbleText.getEmoji(i));
			boton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					bombilla.setVisible(false);
				}
			});
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
		
		bombilla.setVisible(true);
	}

}
