package ro.ionutmarin.iehs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@EntityScan( basePackages = {"ro.ionut"} )
@EnableAutoConfiguration
public class GclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GclientApplication.class, args);
    }

}

