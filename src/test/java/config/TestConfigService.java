package config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"ru.parma.ocr.data.jpa"})
@ComponentScan(basePackages = {"ru.parma.ocr.business.services"})
@EntityScan(basePackages = {"ru.parma.ocr.data.entity"})
public class TestConfigService {

}
