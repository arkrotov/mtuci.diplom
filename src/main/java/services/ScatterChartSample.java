package services;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import mocks.MockApp;
import network.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScatterChartSample {

    private static Map<String, Map<Double,Integer>> map = new HashMap<>();
    private static double max = Double.MIN_VALUE;
    private static double min = Double.MAX_VALUE;

    public static void setEntites(Stream stream) {

        String testApp = stream.getTestApp();
      //  int dstPort = stream.getDstPort();

        double averageSizeOnTransportLayerFromServer = stream.getAverageSizeOnTransportLayerFromServer();
        if (max < averageSizeOnTransportLayerFromServer) max = averageSizeOnTransportLayerFromServer;
        if (min > averageSizeOnTransportLayerFromServer) min = averageSizeOnTransportLayerFromServer;

        System.out.println(testApp + " " + averageSizeOnTransportLayerFromServer);
        Map<Double, Integer> integerIntegerMap = map.get(testApp);

        if (integerIntegerMap == null) {
            integerIntegerMap = new HashMap<>();
            integerIntegerMap.put(averageSizeOnTransportLayerFromServer, 1);
            map.put(testApp, integerIntegerMap);
        } else {
            integerIntegerMap.merge(averageSizeOnTransportLayerFromServer, 1, (a, b) -> (a + b));
        }

        System.out.println(map.hashCode() + " " + map.toString());
    }



    public void start(Stage stage) {
       // stage.setTitle("Scatter Chart Sample");
        System.out.println(max + " " + min);
        final NumberAxis xAxis = new NumberAxis(0, MockApp.getMas().length+1, 1);
        final NumberAxis yAxis = new NumberAxis(0, Math.round(max) + 1, Math.round(min) - 1);
        final ScatterChart<Number, Number> sc = new
                ScatterChart<Number, Number>(xAxis, yAxis);
        xAxis.setLabel("Age (years)");
        yAxis.setLabel("Returns to date");
        sc.setTitle("Investment Overview");

        int count = 0;
        for (Map.Entry<String, Map<Double, Integer>> stringMapEntry : map.entrySet()) {
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

        Scene scene = new Scene(sc, 500, 400);
        stage.setScene(scene);
        stage.show();

    }

}