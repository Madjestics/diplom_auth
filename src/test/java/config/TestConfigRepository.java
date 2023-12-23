package config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"ru.parma.ocr.data.jpa"})
@EntityScan(basePackages = {"ru.parma.ocr.data.entity"})
public class TestConfigRepository {
}
