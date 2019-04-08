package fr.destiny.vacuum.web.config;


import fr.destiny.api.ApiClient;
import fr.destiny.vacuum.web.interceptor.ApiKeyInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties("fr.destiny.api")
@ComponentScan(basePackageClasses = ApiClient.class)
public class VacuumConfig {

    @Bean
    public RestTemplate restTemplate(ApiKeyInterceptor apiKeyInterceptor) {
        return new RestTemplateBuilder().interceptors(apiKeyInterceptor).build();
    }

}
