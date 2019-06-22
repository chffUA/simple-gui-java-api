package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	UI.startLoading();
        SpringApplication.run(Application.class, args);
        UI.doneLoading();
        UI.showLogger();
    }
}
