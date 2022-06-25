package org.j4el.com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan("org.j4el.com")
@EnableJpaAuditing
public class ApplicationConfig {
}
