package zxf.springboot.pa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {
    public Application() {
        log.info("::ctor");
    }

    public static void main(String[] args) {
        log.info("::main");
        SpringApplication.run(Application.class, args);
    }
}

