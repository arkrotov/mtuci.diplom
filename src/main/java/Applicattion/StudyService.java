package Applicattion;


import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StudyService {

    private static RandomForest instance;
    private static Evaluation evaluation;

    public static Classifier getInstance() throws Exception {

        if (instance == null) {

            InputStream trainInputStream = StudyService.class.getClassLoader()
                    .getResourceAsStream("TestStreams.txt");

            InputStream testInputStream = StudyService.class.getClassLoader()
                    .getResourceAsStream("TestStreams.txt");

            BufferedReader trainFile = new BufferedReader(new InputStreamReader(trainInputStream));
            BufferedReader testFile = new BufferedReader(new InputStreamReader(testInputStream));

            Instances trainData = new Instances(trainFile);
            Instances testData = new Instances(testFile);

            trainData.setClassIndex(trainData.numAttributes() - 1);
            testData.setClassIndex(testData.numAttributes() - 1);

            RandomForest classifier = new RandomForest();
            classifier.setNumFeatures(500);

            evaluation = new Evaluation(trainData);
            instance.buildClassifier(trainData);

            evaluation.evaluateModel(instance, testData);

        }

        return instance;
    }

}
