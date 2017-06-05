package Applicattion;

import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;

public class ClassifierService {


    public static void classify (Stream stream) throws Exception {

        MockApp apps = App.app.getDaoService().getApp();

        Classifier classifier = StudyService.getInstance();

        Instance newInstance  = new DenseInstance(15);

        // Сюда передается поток, долго писать обработчик..

        double v = classifier.classifyInstance(newInstance);

        String app = apps.getMas()[(int) Math.round(v)];

        WebService.show(stream, app);
    }


}