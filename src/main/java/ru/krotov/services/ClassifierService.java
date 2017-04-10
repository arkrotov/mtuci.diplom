package ru.krotov.services;

import ru.krotov.classifiers.weka.SentimentClass;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;

/**
 * Created by krotov on 10.04.2017.
 */

public class ClassifierService {

  //  public static

    private NaiveBayesMultinomialText classifier;
    private String modelFive;
    private Instances dataRaw;

    public void FiveWayMNBTrainer (String outputModel) {
        classifier = new NaiveBayesMultinomialText();
        classifier.setLowercaseTokens(true);
        classifier.setUseWordFrequencies(true);

        modelFive = outputModel;

        ArrayList<Attribute> atts = new ArrayList<>(2);
        ArrayList<String> classVal = new ArrayList<String>();

        classVal.add("TCP");
        classVal.add("UDP");

        atts.add(new Attribute("content",(ArrayList<String>)null));
        atts.add(new Attribute("@@class@@",classVal));

        dataRaw = new Instances("TrainingInstances",atts,10);
    }

    public void addTrainingInstance(SentimentClass.ThreeWayClazz threeWayClazz, String[] words) {
//        double[] instanceValue = new double[dataRaw.numAttributes()];
//        instanceValue[0] = dataRaw.attribute(0).addStringValue(Join.join(" ", words));
//        instanceValue[1] = threeWayClazz.ordinal();
//        dataRaw.add(new DenseInstance(1.0, instanceValue));
//        dataRaw.setClassIndex(1);
    }

}
