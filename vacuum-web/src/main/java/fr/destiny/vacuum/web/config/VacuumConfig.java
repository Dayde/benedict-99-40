package fr.destiny.vacuum.web.config;


import fr.destiny.api.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ConfigurationProperties("fr.destiny.api")
@ComponentScan(basePackageClasses = ApiClient.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ApiClient.class))
public class VacuumConfig {

    @Value("${api.key}")
    private String apiKey;

    @Bean
    public ApiClient apiClient() {
        return new ApiClient().addDefaultHeader("X-API-Key", apiKey);
    }
}
