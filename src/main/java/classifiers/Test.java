package classifiers;

import network.Stream;
import network.TestApp;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by me on 23.05.17.
 */
public class Test {


    public Test() {

        String[] nameFields = Stream.getNameFields();
        ArrayList<Attribute> atts = new ArrayList<Attribute>(nameFields.length);

        for (int i = 0; i < nameFields.length; i++) {
            String nameField = nameFields[i];
            atts.add(new Attribute(nameField, i));
        }

        ArrayList<String> classVal = new ArrayList<String>();

        String[] mas = TestApp.getMas();
        classVal.addAll(Arrays.asList(mas));

        atts.add(new Attribute("@@class@@",classVal));

        Instances dataRaw = new Instances("TestInstances",atts,0);
        System.out.println("Before adding any instance");
        System.out.println("--------------------------");
        System.out.println(dataRaw);
        System.out.println("--------------------------");


       // int i = 0;
       // for (Stream stream : streamList) {

            /*DenseInstance denseInstance = new DenseInstance(21);
            denseInstance.setWeight(1.0);
            denseInstance.setValue(0, stream.getAverageSizeOnTransportLayerFromClient());
            denseInstance.setValue(1, stream.getStandardDeviationOfPacketSizeFromClient());
            denseInstance.setValue(2, stream.getAverageSizeOnTransportLayerFromServer());
            denseInstance.setValue(3, stream.getStandardDeviationOfPacketSizeFromServer());
            denseInstance.setValue(4, stream.getAverageSizeDataOnTransportLayerFromClient());
            denseInstance.setValue(5, stream.getStandardDeviationOfDataOnTransportLayerFromClient());
            denseInstance.setValue(6, stream.getAverageSizeDataOnTransportLayerFromServer());
            denseInstance.setValue(7, stream.getStandardDeviationOfDataOnTransportLayerFromServer());
            denseInstance.setValue(8, stream.getAverageNumberOfDataPacketsFromClient());
            denseInstance.setValue(9, stream.getAverageNumberOfDataPacketsFromServer());
            denseInstance.setValue(10, stream.getEfficiencyOfClient());
            denseInstance.setValue(11, stream.getEfficiencyOfClient());
            denseInstance.setValue(12, stream.getRatio());
            denseInstance.setValue(13, stream.getRatioOfData());
            denseInstance.setValue(14, stream.getRatioOfNumberOfPackets());
            denseInstance.setValue(15, stream.getSizeOnTransportLayerFromClient());
            denseInstance.setValue(16, stream.getSizeDataOnTransportLayerFromClient());
            denseInstance.setValue(17, stream.getNumberOfServingsFromClient());
            denseInstance.setValue(18, stream.getSizeOnTransportLayerFromServer());
            denseInstance.setValue(19, stream.getSizeDataOnTransportLayerFromServer());
            denseInstance.setValue(20, stream.getNumberOfServingsFromServer());
            /*

            dataRaw.add(denseInstance);

            System.out.println("After adding instance #" + i++);
            System.out.println("--------------------------");
            System.out.println(dataRaw);
            System.out.println("--------------------------");
        }*/



    }

    public static void main(String[] args) {
        new Test();
    }

    public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
        Instances[][] split = new Instances[2][numberOfFolds];

        for (int i = 0; i < numberOfFolds; i++) {
            split[0][i] = data.trainCV(numberOfFolds, i);
            split[1][i] = data.testCV(numberOfFolds, i);
        }

        return split;
    }
}
