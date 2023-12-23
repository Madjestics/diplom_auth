package diplom.auth;

import diplom.auth.web.config.SecurityConfig;
import diplom.auth.web.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityConfig.class, SwaggerConfig.class})
public class AuthApplication {

    public static void main(String[] args){
        SpringApplication.run(AuthApplication.class, args);
    }

}
