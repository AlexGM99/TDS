package Pruebas;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Test {

	public static void main(String[] args) throws Exception {
		
		Map<String, Integer> mapa = new HashMap<String, Integer>(10);
		
		mapa.put("Nombre1", 3);
		mapa.put("Nombre2", 4);
		mapa.put("Nombre3", 5);
		mapa.put("Nombre4", 1);
		
		System.out.println("mapa" + mapa);
		
		Map<String, Integer> mapa1 = mapa.entrySet().stream()
						.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
						.limit(3)
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		System.out.println("mapa1" + mapa1);
		
		
		Map<String, Double> mapa2 = new LinkedHashMap<String, Double>(mapa1.size());
		for (String it : mapa1.keySet()) {
			mapa2.put(it, new Double(mapa1.get(it)));
		}
		
		System.out.println("mapa2" + mapa1);
		
		int total = 13;
		
		mapa2.entrySet().stream()
		.forEach(e -> e.setValue(e.getValue() * 100 / total));
		
		System.out.println("mapa2 calculado" + mapa2);
		
		 
		double[] xData = new double[] { 1.0, 2.0, 3.0 };
	    double[] yData = new double[] { 34.0, 52.0, 27.0 };
		
		/*
		 * //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)",
		 * xData, yData); XYChart chart = new XYChart(500, 400);
		 * chart.setTitle("Ejemplo"); chart.setXAxisTitle("Mes");
		 * chart.setYAxisTitle("Mensajes enviados"); XYSeries series =
		 * chart.addSeries("y(x)", xData, yData);
		 * series.setMarker(SeriesMarkers.CIRCLE);
		 * 
		 * BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapFormat.PNG);
		 * 
		 * // Show it new SwingWrapper(chart).displayChart();
		 */
	    
	    
	    // Histograma
	    // Create Chart
	    //CategoryChart chart1 = new CategoryChartBuilder().width(800).height(600).title("Histograma de prueba").xAxisTitle("Mes").build();	 
	    CategoryChart chart1 = new CategoryChartBuilder().xAxisTitle("Mes").build();	 
	    // Customize Chart
	    chart1.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart1.getStyler().setHasAnnotations(true);
	    //chart1.getStyler().setSeriesColors(new Color[] {new Color(24, 126, 254)});
	    // Series
	    chart1.addSeries("Mensajes enviados", Arrays.asList(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo" }), Arrays.asList(new Integer[] { 45, 92, 27, 34, 87 }));	 
	    // Display
	    //new SwingWrapper(chart1).displayChart();
	    // Save
	    BitmapEncoder.saveBitmap(chart1, "./Sample_Chart-H", BitmapFormat.PNG);
	    
	    // Tarta
	    // Create Chart
	    //PieChart chart2 = new PieChartBuilder().width(800).height(600).title("Tarta de prueba").build();
	    PieChart chart2 = new PieChartBuilder().build();
		 
	    // Customize Chart
	    Color[] sliceColors = new Color[] { new Color(255, 39, 38), new Color(255, 204, 51), new Color(255, 254, 39), new Color(127, 254, 23), new Color(24, 126, 254), new Color(181, 0, 251) };
	    chart2.getStyler().setSeriesColors(sliceColors);
	    chart2.getStyler().setLegendPosition(LegendPosition.InsideNW);
	 
	    // Series
	    chart2.addSeries("Verano", 97);
	    chart2.addSeries("[FIUM] 3º G2", 21);
	    chart2.addSeries("Anime Alcoholico", 5);
	    chart2.addSeries("NAMAE", 17);
	    chart2.addSeries("Texto aquí", 40);
	    // Display
	    new SwingWrapper(chart2).displayChart();
	    
	    BitmapEncoder.saveBitmap(chart2, "./Sample_Chart-T", BitmapFormat.PNG);

	}

}

/*
public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    return map.entrySet()
              .stream()
           .sorted(Map.Entry.comparingByValue())
              .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (e1, e2) -> e1, 
                LinkedHashMap::new
              ));
}
*/