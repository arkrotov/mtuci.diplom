import jpcap.JpcapCaptor;
import network.Captor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {
       // SpringApplication.run(Main.class, args);

        JpcapCaptor instance = Captor.getInstance();

        for (int i = 0; i < 10000; i++)  {
            System.out.println(instance.getPacket());
            Thread.sleep(200);
        }
    }
}
