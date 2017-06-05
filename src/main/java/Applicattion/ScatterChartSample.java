package Applicattion;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class ScatterChartSample {

    private static Map<String, Map<Double,Integer>> appToValueCountMap = new HashMap<>();
    private static double max = Double.MIN_VALUE;
    private static double min = Double.MAX_VALUE;
    private static String metric;

    public static void setEntites(String metricValue, String app, String metric) {

        Double value = Double.parseDouble(metricValue);

        if (max < value) max = value;
        if (min > value) min = value;

        System.out.println(app + " " + value);
        Map<Double, Integer> valueToCountMap = appToValueCountMap.get(app);

        if (valueToCountMap == null) {
            valueToCountMap = new HashMap<>();
            valueToCountMap.put(value, 1);
            appToValueCountMap.put(app, valueToCountMap);
        } else {
            valueToCountMap.merge(value, 1, (a, b) -> (a + b));
        }

        ScatterChartSample.metric = metric;
    }




    public void start(Stage stage) {

        final NumberAxis xAxis = new NumberAxis(0, MockApp.getMas().length+1, 1);
        final NumberAxis yAxis = new NumberAxis((Math.round(min) - 1) < 0? 0: Math.round(min) - 1, Math.round(max) + 1, (max-min)/10);
        final ScatterChart<Number, Number> sc = new
                ScatterChart<Number, Number>(xAxis, yAxis);

        xAxis.setLabel("Application");
        yAxis.setLabel(metric);
        sc.setTitle("Investment Overview");

        int count = 0;
        for (Map.Entry<String, Map<Double, Integer>> stringMapEntry : appToValueCountMap.entrySet()) {
            XYChart.Series series = new XYChart.Series();
            series.setName(stringMapEntry.getKey());

            count++;
            Map<Double, Integer> childMap = stringMapEntry.getValue();

            for (Map.Entry<Double, Integer> integerIntegerEntry : childMap.entrySet()) {

                double pop = count;
                boolean bol = false;

                Double key = integerIntegerEntry.getKey();
                for (int i = 1; i <= integerIntegerEntry.getValue(); i++) {
                    series.getData().add(new XYChart.Data(pop, key));


                    if (!bol) {
                        if (pop < 0) pop = - pop;
                        pop += 0.01;
                        bol = true;
                    } else {
                        pop = - pop;
                        bol = false;
                    }
                }

            }

            ObservableList<XYChart.Series<Number, Number>> data = sc.getData();
            data.addAll(series);
        }

        Scene scene = new Scene(sc, 600, 600);
        stage.setScene(scene);
        stage.show();

    }

}