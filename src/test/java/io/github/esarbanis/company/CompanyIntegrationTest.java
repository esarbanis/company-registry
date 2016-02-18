package io.github.esarbanis.company;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import io.github.esarbanis.BaseIntegrationTest;
import io.github.esarbanis.GlobalExceptionHandler;
import io.github.esarbanis.PageImplBean;
import io.github.esarbanis.ServerApplication;
import io.github.esarbanis.company.model.Company;
import io.github.esarbanis.company.model.Owner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

/**
 * @author SARMPANE
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompanyIntegrationTest extends BaseIntegrationTest {

    @Test
    public void _0CreateCompany() throws Exception {
        Company company = Company.builder()
                .name("Test Company")
                .address("Test Address")
                .city("Test City")
                .country("Test Country")
                .phone("Test Phone")
                .email("email@mail.com")
                .owners(Collections.singletonList(Owner.builder()
                        .name("Test Owner").build())).build();
        RequestEntity<Company> requestEntity = RequestEntity.post(URI.create("/company")).header("Authorization", "Basic ZGVtbzpkZW1v").body(company);
        // arrange
        ParameterizedTypeReference<Company> responseType =
                new ParameterizedTypeReference<Company>() {
                };
        // act
        ResponseEntity<Company> responseEntity =
                exchange("/company", POST, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.CREATED, statusCode);

        Company companyResponse = responseEntity.getBody();

        assertNotNull(companyResponse);
    }

    @Test
    public void _1CreateInvalidCompany() throws Exception {
        Company company = Company.builder()
                .name("Test Company")
                .address("Test Address")
                .country("Test Country")
                .phone("Test Phone")
                .email("invalid")
                .owners(Collections.singletonList(Owner.builder()
                        .name("Test Owner").build())).build();
        RequestEntity<Company> requestEntity = RequestEntity.post(URI.create("/company")).header("Authorization", "Basic ZGVtbzpkZW1v").body(company);
        // arrange
        ParameterizedTypeReference<Collection<GlobalExceptionHandler.Violation>> responseType =
                new ParameterizedTypeReference<Collection<GlobalExceptionHandler.Violation>>() {
                };
        // act
        ResponseEntity<Collection<GlobalExceptionHandler.Violation>> responseEntity =
                exchange("/company", POST, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);

        Collection<GlobalExceptionHandler.Violation> violations = responseEntity.getBody();

        assertNotNull(violations);
        assertTrue(violations.stream()
                .map(GlobalExceptionHandler.Violation::getPath)
                .allMatch(s -> Arrays.asList("city", "email").contains(s)));
    }

    @Test
    public void _2CreateAnotherCompany() throws Exception {
        Company company = Company.builder()
                .name("Test Company 2")
                .address("Test Address 2")
                .city("Test City 2")
                .country("Test Country 2")
                .phone("Test Phone 2")
                .email("email2@mail.com")
                .owners(Collections.singletonList(Owner.builder()
                        .name("Test Owner 2").build())).build();
        RequestEntity<Company> requestEntity = RequestEntity.post(URI.create("/company")).header("Authorization", "Basic ZGVtbzpkZW1v").body(company);
        // arrange
        ParameterizedTypeReference<Company> responseType =
                new ParameterizedTypeReference<Company>() {
                };
        // act
        ResponseEntity<Company> responseEntity =
                exchange("/company", POST, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.CREATED, statusCode);

        Company companyResponse = responseEntity.getBody();

        assertNotNull(companyResponse);
    }

    @Test
    public void _3GetCompanies() throws Exception {
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("/company")).header("Authorization", "Basic ZGVtbzpkZW1v").build();
        // arrange
        ParameterizedTypeReference<PageImplBean<Company>> responseType =
                new ParameterizedTypeReference<PageImplBean<Company>>() {
                };
        // act
        ResponseEntity<PageImplBean<Company>> responseEntity =
                exchange("/company", GET, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);

        PageImplBean<Company> companies = responseEntity.getBody();

        assertEquals(2, companies.getTotalElements());
    }

    @Test
    public void _3GetCompany() throws Exception {
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("/company/1")).header("Authorization", "Basic ZGVtbzpkZW1v").build();
        // arrange
        ParameterizedTypeReference<Company> responseType =
                new ParameterizedTypeReference<Company>() {
                };
        // act
        ResponseEntity<Company> responseEntity =
                exchange("/company/1", GET, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);

        Company company = responseEntity.getBody();

        assertNotNull(company);
    }

    @Test
    public void _4UpdateFirstCompany() throws Exception {
        RequestEntity<Void> getRequestEntity = RequestEntity.get(URI.create("/company/1")).header("Authorization", "Basic ZGVtbzpkZW1v").build();
        ParameterizedTypeReference<Company> getResponseType =
                new ParameterizedTypeReference<Company>() {};
        Company company = exchange("/company/1", GET, getRequestEntity, getResponseType).getBody();
        company.setName("Awesomeness Inc.");
        RequestEntity<Company> requestEntity = RequestEntity.put(URI.create("/company/1")).header("Authorization", "Basic ZGVtbzpkZW1v").body(company);
        // arrange
        ParameterizedTypeReference<Company> responseType =
                new ParameterizedTypeReference<Company>() {
                };
        // act
        ResponseEntity<Company> responseEntity =
                exchange("/company/1", PUT, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);

        Company companyResponse = responseEntity.getBody();

        assertNotNull(companyResponse);
        assertEquals("Awesomeness Inc.", companyResponse.getName());
    }

    @Test
    public void _5UpdateNonExistingCompany() throws Exception {
        Company company = Company.builder()
                .name("Test Company 2")
                .address("Test Address 2")
                .city("Test City 2")
                .country("Test Country 2")
                .phone("Test Phone 2")
                .email("email2@mail.com")
                .owners(Collections.singletonList(Owner.builder()
                        .name("Test Owner 2").build())).build();
        RequestEntity<Company> requestEntity = RequestEntity.put(URI.create("/company/5")).header("Authorization", "Basic ZGVtbzpkZW1v").body(company);
        // arrange
        ParameterizedTypeReference<Company> responseType =
                new ParameterizedTypeReference<Company>() {
                };
        // act
        ResponseEntity<Company> responseEntity =
                exchange("/company/5", PUT, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, statusCode);
    }

    @Test
    public void _6DeleteExistingCompany() throws Exception {
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("/company/1")).header("Authorization", "Basic ZGVtbzpkZW1v").build();
        // arrange
        ParameterizedTypeReference<Void> responseType =
                new ParameterizedTypeReference<Void>() {
                };
        // act
        ResponseEntity<Void> responseEntity =
                exchange("/company/1", DELETE, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.NO_CONTENT, statusCode);
    }

    @Test
    public void _7DeleteNonExistingCompany() throws Exception {
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("/company/5")).header("Authorization", "Basic ZGVtbzpkZW1v").build();
        // arrange
        ParameterizedTypeReference<Void> responseType =
                new ParameterizedTypeReference<Void>() {
                };
        // act
        ResponseEntity<Void> responseEntity =
                exchange("/company/5", DELETE, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, statusCode);
    }

    @Test
    public void _8ConfirmDeletion() throws Exception {
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("/company")).header("Authorization", "Basic ZGVtbzpkZW1v").build();
        // arrange
        ParameterizedTypeReference<PageImplBean<Company>> responseType =
                new ParameterizedTypeReference<PageImplBean<Company>>() {
                };
        // act
        ResponseEntity<PageImplBean<Company>> responseEntity =
                exchange("/company", GET, requestEntity, responseType);
        // assert
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);

        PageImplBean<Company> companies = responseEntity.getBody();

        assertEquals(1, companies.getTotalElements());
    }
}
