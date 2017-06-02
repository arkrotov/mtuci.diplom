package services;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.FastVector;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClassifierService {

    private static Evaluation classify(weka.classifiers.Classifier model,
                                       Instances trainingSet, Instances testingSet) throws Exception {
        Evaluation evaluation = new Evaluation(trainingSet);

        model.buildClassifier(trainingSet);
        evaluation.evaluateModel(model, testingSet);

        return evaluation;
    }


    private static double calculateAccuracy(FastVector predictions) {
        double correct = 0;

        for (int i = 0; i < predictions.size(); i++) {
            NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
            if (np.predicted() == np.actual()) {
                correct++;
            }
        }

        return 100 * correct / predictions.size();
    }

    private static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
        Instances[][] split = new Instances[2][numberOfFolds];

        for (int i = 0; i < numberOfFolds; i++) {
            split[0][i] = data.trainCV(numberOfFolds, i);
            split[1][i] = data.testCV(numberOfFolds, i);
        }

        return split;
    }

    public static void run () throws Exception {

        InputStream inputStream = ClassifierService.class.getClassLoader()
                .getResourceAsStream("TestStreams.txt");

        BufferedReader datafile = new BufferedReader(new InputStreamReader(inputStream));


        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);

        Instances[][] split = crossValidationSplit(data, 4000);

        Instances[] trainingSplits = split[0];
        Instances[] testingSplits = split[1];



        List<Classifier> models = new ArrayList<>();

        for (int i = 1; i < 500; i++) {
            RandomForest randomForest = new RandomForest();
            randomForest.setNumFeatures(i);
        }


        for (Classifier model : models) {

            try {
                // Collect every group of predictions for current model in a FastVector
                FastVector predictions = new FastVector();

                // For each training-testing split pair, train and MockApp the classifier
                for (int i = 0; i < trainingSplits.length; i++) {
                    Evaluation validation = classify(model, trainingSplits[i], testingSplits[i]);


                    predictions.appendElements(validation.predictions());

                    // Uncomment to see the summary for each training-testing pair.
                    //System.out.println(network.models[j].toString());
                }

                // Calculate overall accuracy of current classifier on all splits
                double accuracy = calculateAccuracy(predictions);

                // Print current classifier's name and accuracy in a complicated,
                // but nice-looking way.
                System.out.println("Accuracy of " + model.getClass().getSimpleName() + ": "
                        + String.format("%.2f%%", accuracy)
                        + "\n---------------------------------");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}