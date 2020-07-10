package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import modelo.Contacto;
import tds.BubbleText;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class Test1 {
	
	private JFrame window;
	private DefaultListModel<Integer> listModel;
	private JList<Integer> chatslist;
	private JScrollPane scrollChats;
	private JPanel panel;


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
		window = new JFrame();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setBounds(100, 100, 328, 119);
		window.setVisible(true);
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = window.getSize();
        window.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	
        panel = new JPanel();
		panel.setAutoscrolls(true);
		window.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1, 0, 0, 0, 21, 75, 165, 0, 35, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 28, 0, 37, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
        
        scrollChats = new JScrollPane();
		GridBagConstraints gbc_scrollChats = new GridBagConstraints();
		gbc_scrollChats.gridheight = 2;
		gbc_scrollChats.gridwidth = 4;
		gbc_scrollChats.insets = new Insets(0, 0, 0, 5);
		gbc_scrollChats.fill = GridBagConstraints.BOTH;
		gbc_scrollChats.gridx = 1;
		gbc_scrollChats.gridy = 3;
		panel.add(scrollChats, gbc_scrollChats);
		
		listModel = new DefaultListModel<Integer>();
		
		chatslist = new JList<Integer>(listModel);
		scrollChats.setViewportView(chatslist);
			
		/*JPanel panel1 = new JPanel();
		panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
		bombilla.setContentPane(panel1);
		panel1.setLayout(new BorderLayout(0, 0));*/
		
		JPanel panel2 = new JPanel();
		panel2.setVisible(true);
		window.add(panel2);
		//panel1.add(panel2, BorderLayout.SOUTH);
			
		JScrollPane scroll_chat = new JScrollPane();
		GridBagConstraints gbc_scroll_chat = new GridBagConstraints();
		gbc_scroll_chat.gridheight = 2;
		gbc_scroll_chat.gridwidth = 5;
		gbc_scroll_chat.insets = new Insets(0, 0, 5, 0);
		gbc_scroll_chat.fill = GridBagConstraints.BOTH;
		gbc_scroll_chat.gridx = 5;
		gbc_scroll_chat.gridy = 2;
		panel2.add(scroll_chat, gbc_scroll_chat);
		
		//DefaultListModel<JButton>listModel = new DefaultListModel<JButton>();
		//JList<JButton>chat_list = new JList<JButton>();
		//scroll_chat.setViewportView(chat_list);

		
		LinkedList<Integer> emojis = new LinkedList<Integer>();
		
		for (int i = 0; i < BubbleText.MAXICONO; i++) {
			/*JButton boton = new JButton();
			Icon icono = BubbleText.getEmoji(i);
			boton.setIcon(icono);
			boton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//System.out.println(icono);
					JFrame aux1 = new JFrame();
					aux1.setMinimumSize(new Dimension(100, 100));
					JLabel aux2 = new JLabel();
					aux2.setIcon(icono);
					aux1.getContentPane().add(aux2);
					aux1.setVisible(true);
				}
			});*/
			//chat_list.add(boton);
			//panel2.add(boton);
			
			emojis.add(new Integer(i));
		}
		
		setChats(emojis);
		
		
		
		//scroll_chat.add(chat_list);
		//scroll_chat.setViewportView(chat_list);
		//scroll_chat.setVisible(true);
	}
	
	
	
	
	public void setChats(LinkedList<Integer> listaModel) {
		listModel = new DefaultListModel<Integer>();
		listaModel.stream().forEach(cont -> listModel.addElement(cont));
		chatslist = new JList<Integer>(listModel);
		scrollChats.setViewportView(chatslist);
		chatslist.setCellRenderer(new chatListRender());
	}
	
	private class chatListRender extends JLabel implements ListCellRenderer<Integer> {
		public chatListRender() {
		    setOpaque(true);
		}
	    public Component getListCellRendererComponent(JList<? extends Integer> list, Integer numEmoji, int index,
	        boolean isSelected, boolean cellHasFocus) {
	    	
	    	this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    	
    		this.setIcon(BubbleText.getEmoji(numEmoji));
	    	
	        return this;
	    }
	}

}
