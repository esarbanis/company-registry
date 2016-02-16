package io.github.esarbanis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.junit.Before;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author Efthymios Sarmpanis
 */
@WebAppConfiguration
@IntegrationTest(value = "server.port=9000")
public abstract class BaseIntegrationTest {

    private static final String CONTEXT = "http://localhost:9000/%s";

    private RestTemplate restTemplate;

    @Before
    public void setup() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                MediaType.parseMediaTypes("application/json,application/hal+json"));
        converter.setObjectMapper(mapper);

        restTemplate = new TestRestTemplate();
        restTemplate
                .setMessageConverters(Collections.<HttpMessageConverter<?>>singletonList(converter));
    }

    protected <T> ResponseEntity<T> exchange(String path, HttpMethod method,
                                             HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType,
                                             Object... uriVariables) throws RestClientException {
        return restTemplate.exchange(String.format(CONTEXT, path), method, requestEntity, responseType,
                uriVariables);
    }

}
