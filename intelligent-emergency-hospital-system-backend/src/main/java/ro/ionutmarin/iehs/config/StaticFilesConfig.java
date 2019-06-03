package ro.ionutmarin.iehs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticFilesConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

            registry.addResourceHandler("/js/**")
                    .addResourceLocations("classpath:/templates/js/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
    }
}
