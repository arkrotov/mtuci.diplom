package Applicattion;

import lombok.Data;

import java.io.IOException;

/**
 * Created by me on 05.06.17.
 */

@Data
public class App {

    public static boolean loadFromBase = false;

    static App app;
    private DaoService daoService;

    public static void main(String[] args) throws IOException {
        app = new App();
    }

    private App() throws IOException {
        init();
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
