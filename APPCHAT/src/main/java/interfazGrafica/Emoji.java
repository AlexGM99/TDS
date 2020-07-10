package interfazGrafica;

import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionListener;

import controlador.ControladorVistaAppChat;
import tds.BubbleText;

public class Emoji {
	
	private JFrame frame;
	private JList<Integer> jList;
	private JScrollPane pane;
	private ControladorVistaAppChat controlador;
	

	public Emoji(ControladorVistaAppChat controlador, int codeChat) {
		// Inicializar JFrame
		this.controlador = controlador;
		frame = new JFrame("Emojis");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(150, 300));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// Obtener iconos
		List<Integer> emoticonos = new LinkedList<Integer>();
		for (int i = 0; i < BubbleText.MAXICONO; i++) {
			emoticonos.add(i);
		}
		// Guardar iconos en una lista
		jList = new JList<>(emoticonos.toArray(new Integer[emoticonos.size()]));
		jList.setCellRenderer(createListRenderer());
		jList.addListSelectionListener(createListSelectionListener(jList, codeChat));
		
		pane = new JScrollPane(jList);
		frame.add(pane);
	}

	// Listener que llama al controlador para enviar el mensaje con el emoji
	private ListSelectionListener createListSelectionListener(JList<Integer> list, int codeChat) {
		return e -> {
			if (!e.getValueIsAdjusting()) {
				controlador.sendEmoji(list.getSelectedValue(), codeChat);
				//System.out.println("emoji: " + list.getSelectedValue());
			}
		};
	}

	// Renderiza la imagen del emoticono
	private ListCellRenderer<? super Integer> createListRenderer() {
		return new DefaultListCellRenderer() {
			private static final long serialVersionUID = 416372501922746920L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (c instanceof JLabel) {
					JLabel label = (JLabel) c;
					Integer emp = (Integer) value;
					label.setIcon(BubbleText.getEmoji(emp));
				}
				return c;
			}
		};
	}
}
