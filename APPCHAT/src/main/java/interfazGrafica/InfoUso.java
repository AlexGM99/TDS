package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.style.Styler.LegendPosition;

import controlador.ControladorVistaAppChat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class InfoUso extends JFrame {

	private static final long serialVersionUID = -3685602735969574874L;

	private JPanel contentPane;

	public static final List<String> MESES_YEAR = Arrays.asList(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec" });
	public static final Color[] GRAFICA_HISTOGRAMA_COLOR = new Color[] { new Color(243, 180, 159) };
	public static final String GRAFICA_HISTOGRAMA_PATH = System.getProperty("user.home") + "/Grafica-Uso-Anual";
	public static final BitmapFormat GRAFICA_HISTOGRAMA_FORMATO = BitmapFormat.PNG;
	public static final Color[] GRAFICA_TARTA_COLORES = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
	public static final String GRAFICA_TARTA_PATH = System.getProperty("user.home") + "/Grafica-Uso-Grupos";
	public static final BitmapFormat GRAFICA_TARTA_FORMATO = BitmapFormat.PNG;

	/**
	 * Create the frame.
	 */
	public InfoUso(ControladorVistaAppChat controlador) {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 328, 119);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = getSize();
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("Yearly use");
		panel.add(btnNewButton);

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<Integer> valores = controlador.getInformacionUsoAnual();

				// Crear histograma con los mensajes por mes del user 
				CategoryChart graficaHistograma = new CategoryChartBuilder().title("Messages sent in " + LocalDate.now().getYear()).xAxisTitle("Month").build();	 
				// Personalizar gráfico
				graficaHistograma.getStyler().setLegendPosition(LegendPosition.InsideNW);
				graficaHistograma.getStyler().setHasAnnotations(true);
				graficaHistograma.getStyler().setSeriesColors(GRAFICA_HISTOGRAMA_COLOR);
				// Valores
				graficaHistograma.addSeries("Messages sent", MESES_YEAR, valores);	 
				//Mostrar
				XChartPanel<CategoryChart> grafica = new XChartPanel<CategoryChart>(graficaHistograma);
				JFrame ventana = new JFrame();
				ventana.add(grafica);
				ventana.setPreferredSize(new Dimension(800, 600));
				ventana.setMinimumSize(new Dimension(800, 600));
				ventana.setLocationRelativeTo(null);
				ventana.setVisible(true);
				ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				grafica.setVisible(true);
				// Guardar	    
				try {
					BitmapEncoder.saveBitmap(graficaHistograma, GRAFICA_HISTOGRAMA_PATH, GRAFICA_HISTOGRAMA_FORMATO);
				} catch (IOException e3) {
					//e3.printStackTrace();
					JOptionPane.showMessageDialog(null, "There was an error showing your use info", "Boom!", JOptionPane.ERROR_MESSAGE);
				}			
			}
		});

		JButton btnNewButton_1 = new JButton("Group most used");
		panel.add(btnNewButton_1);

		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Map<String, Double> valores = controlador.getInformacionUsoGrupos();

				// Crear diagrama de tarta con los 6 grupos
				PieChart graficaTarta = new PieChartBuilder().title("Most messages sent to groups").build();
				// Personalizar gráfico
				graficaTarta.getStyler().setSeriesColors(GRAFICA_TARTA_COLORES);
				graficaTarta.getStyler().setLegendPosition(LegendPosition.InsideNW);
				// Valores
				for (String it : valores.keySet()) {
					graficaTarta.addSeries(it, valores.get(it));
				}
				//Mostrar
				XChartPanel<PieChart> grafica = new XChartPanel<PieChart>(graficaTarta);
				JFrame ventana = new JFrame();
				ventana.add(grafica);
				ventana.setPreferredSize(new Dimension(800, 600));
				ventana.setMinimumSize(new Dimension(800, 600));
				ventana.setLocationRelativeTo(null);
				ventana.setVisible(true);
				ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				grafica.setVisible(true);

				// Guardar	    
				try {
					BitmapEncoder.saveBitmap(graficaTarta, GRAFICA_TARTA_PATH, GRAFICA_TARTA_FORMATO);
				} catch (IOException e3) {
					//e3.printStackTrace();
					JOptionPane.showMessageDialog(null, "There was an error showing your use info", "Boom!", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Choose the chart");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel);
	}

}
