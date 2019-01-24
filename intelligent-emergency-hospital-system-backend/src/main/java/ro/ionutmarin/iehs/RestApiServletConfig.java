package ro.ionutmarin.iehs;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan
public class RestApiServletConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8888", "http://localhost:8081")
                //.allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS");
                //.allowedMethods("*");
    }

}