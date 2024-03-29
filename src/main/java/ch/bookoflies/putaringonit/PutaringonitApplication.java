package ch.bookoflies.putaringonit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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
        System.out.println("test");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedOrigins("**", "https://putaringonit-angular.oa.r.appspot.com/api", "https://s-und-f.herokuapp.com/")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowCredentials(true);
            }
        };
    }

}
