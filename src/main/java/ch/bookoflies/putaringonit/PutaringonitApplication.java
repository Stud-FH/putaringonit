package ch.bookoflies.putaringonit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@RestController
@SpringBootApplication
public class PutaringonitApplication {

    public static void main(String[] args) {
        SpringApplication.run(PutaringonitApplication.class, args);
    }


    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String helloWorld() {
        return "Hello World " + LocalDateTime.now();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("localhost:4200", "https://putaringonit-angular.oa.r.appspot.com/api", "https://s-und-f.herokuapp.com/")
                        .allowedMethods("*");
            }
        };
    }

}
