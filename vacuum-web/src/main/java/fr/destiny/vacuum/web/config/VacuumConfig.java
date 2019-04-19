package fr.destiny.vacuum.web.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.destiny.api.ApiClient;
import fr.destiny.api.client.Destiny2Api;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;

@Configuration
@ConfigurationProperties("fr.destiny.api")
@ComponentScan(basePackageClasses = ApiClient.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ApiClient.class))
public class VacuumConfig {

    public static final String BUNGIE_ROOT_URL = "https://www.bungie.net";

    @Value("${api.key}")
    private String apiKey;

    @Bean
    public ApiClient apiClient() {
        return new ApiClient().addDefaultHeader("X-API-Key", apiKey);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public DataSource dataSource(RestTemplate restTemplate, Destiny2Api api) {
        String path = api.destiny2GetDestinyManifest()
                .getResponse()
                .getMobileWorldContentPaths()
                .get("en");
        String[] split = path.split("/");
        Path resourcePath = Paths.get(System.getProperty("java.io.tmpdir")).resolve(split[split.length - 1]);

        if (!Files.exists(resourcePath)) {
            downloadLatestManifest(restTemplate, path, resourcePath);
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite::resource:" + resourcePath.toUri());
        return dataSource;
    }

    private void downloadLatestManifest(RestTemplate restTemplate, String path, Path resourcePath) {
        byte[] manifestZippedFile = restTemplate.getForObject(BUNGIE_ROOT_URL + path, byte[].class);

        try (
                ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(manifestZippedFile));
                OutputStream outputStream = Files.newOutputStream(resourcePath)
        ) {
            zipInputStream.getNextEntry();
            IOUtils.copy(zipInputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
