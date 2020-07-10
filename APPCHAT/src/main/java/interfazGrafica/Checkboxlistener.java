package interfazGrafica;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionListener;

import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Usuario;
import tds.BubbleText;

import javax.swing.JList;

public class Checkboxlistener extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8410336207635278644L;
	private JPprivate class chatListRender extends JLabel implements ListCellRenderer<Integer> {
		public chatListRender() {
		    setOpaque(true);
		}
	    public Component getListCellRendererComponent(JList<? extends Integer> list, Integer numEmoji, int index,
	        boolean isSelected, boolean cellHasFocus) {
	    	this.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    	
    		this.setIcon(BubbleText.getEmoji(numEmoji));
	    	
	        return this;
	    }
	}anel jpAcc = new JPanel();
	private JList<JButton> checkBoxesJList;

	Checkboxlistener() {
		jpAcc.setLayout(new BorderLayout());
		//String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		JButton labels[] = { new JButton("Hola"), new JButton("Mundo") };
		checkBoxesJList = new JList<JButton>(labels);

		checkBoxesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(checkBoxesJList);

		jpAcc.add(scrollPane);

		getContentPane().add(jpAcc);
		pack();



		JList<Usuario> listaUsuarios;
		listaUsuarios.setCellRenderer(createListRenderer());
		listaUsuarios.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				System.out.println(
						listaUsuarios.getSelectedValue().getNombre());}
		});
		JScrollPane pane = new JScrollPane(listaUsuarios);
		panel.add(pane); 


	}
}
