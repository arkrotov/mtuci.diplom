package Applicattion;

import lombok.Data;

import java.io.IOException;

@Data
public class App {

    public static boolean loadFromBase = false;

    public static App app;
    private WebService webService;
    private ClassifierService classifierService;
    private StudyService studyService;
    private DaoService daoService;

    public static void main(String[] args) throws IOException {
        app = new App();
    }

    private App() throws IOException {
        init();
        webService = new WebService();
        classifierService = new ClassifierService();
    }


    private void init() throws IOException {

        daoService = new DaoService();

        if (loadFromBase) {
            daoService.pull();
        }


        Thread captorService = new Thread(new CaptorService());
        captorService.start();

    }
}
