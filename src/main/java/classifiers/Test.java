package classifiers;

import network.Stream;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;

/**
 * Created by me on 23.05.17.
 */
public class Test {


    public Test() {

        ArrayList<Attribute> atts = new ArrayList<Attribute>(2);

        atts.add(new Attribute("name", true));
        atts.add(new Attribute("age"));
        atts.add(new Attribute("city", true));
        atts.add(new Attribute("birth", true));

        ArrayList<String> classVal = new ArrayList<String>();

        // TODO: Вынести классы в отдельный класс \ файл
        classVal.add("firstApp");
        classVal.add("secondApp");
        classVal.add("thirdApp");
        classVal.add("fourthApp");
        classVal.add("fifthApp");
        classVal.add("sixthApp");
        classVal.add("seventhApp");
        classVal.add("eighthApp");
        classVal.add("ninthApp");
        classVal.add("tenthApp");

        atts.add(new Attribute("@@class@@",classVal));

        Instances dataRaw = new Instances("TestInstances",atts,0);
        System.out.println("Before adding any instance");
        System.out.println("--------------------------");
        System.out.println(dataRaw);
        System.out.println("--------------------------");


        Stream stream = new Stream(null);

        DenseInstance denseInstance = new DenseInstance(5);
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

        dataRaw.add(denseInstance);

        System.out.println("Before adding any instance");
        System.out.println("--------------------------");
        System.out.println(dataRaw);
        System.out.println("--------------------------");

    }

    public static void main(String[] args) {
        new Test();
    }
}
