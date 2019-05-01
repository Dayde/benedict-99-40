package fr.destiny.benedict.web.config;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.destiny.api.ApiClient;
import fr.destiny.api.client.Destiny2Api;
import fr.destiny.api.client.UserApi;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

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
@ComponentScan(
        basePackageClasses = ApiClient.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = {
                        ApiClient.class,
                        Destiny2Api.class,
                        UserApi.class
                }
        )
)
public class BenedictConfig {

    public static final String BUNGIE_ROOT_URL = "https://www.bungie.net";

    @Value("${BUNGIE_API_KEY}")
    private String API_KEY;

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Qualifier("destiny2ApiScoped")
    public Destiny2Api destiny2ApiScoped() {
        return new Destiny2Api(apiClientScoped());
    }

    @Bean
    @Qualifier("destiny2Api")
    public Destiny2Api destiny2Api() {
        return new Destiny2Api(apiClient());
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Qualifier("userApiScoped")
    public UserApi userApiScoped() {
        return new UserApi(apiClientScoped());
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Qualifier("apiClientScoped")
    public ApiClient apiClientScoped() {
        return new ApiClient().addDefaultHeader("X-API-Key", API_KEY);
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient().addDefaultHeader("X-API-Key", API_KEY);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
    }

    @Bean
    public DataSource dataSource(RestTemplate restTemplate, Destiny2Api destiny2Api) {
        String path = destiny2Api.destiny2GetDestinyManifest()
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

        if (manifestZippedFile == null) {
            throw new RuntimeException("Manifest couldn't be retrieved.");
        }

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
