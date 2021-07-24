package com.fun.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CheckoutServiceTest {
    /* Test Ids */
    private final static String VALID_CUSTOMER_ID = "C123";
    private final static String NOTFOUND_CUSTOMER_ID = "C999";
    private final static String INVALID_CUSTOMER_ID = "99999";
    private final static String VALID_PRODUCT_ID = "P123";
    private final static String VALID_PRODUCT_ID_1 = "P1234";
    private final static String INVALID_PRODUCT_ID = "123";

    /* URL SET */
    private final static String BASKET_URL = "/api/v1/checkout/customer/{customerId}";
    private final static String ADD_PRODUCT_URL = BASKET_URL +"/product/{productId}";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void swagger() {
        ResponseEntity<String> swaggerResponse = testRestTemplate.getForEntity("/swagger", String.class);
        assertThat(swaggerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(swaggerResponse.getBody()).contains("swagger-ui");
        assertThat(swaggerResponse.getHeaders().getContentType()).isEqualTo(MediaType.TEXT_HTML);
    }

    @Test
    void addProduct() {
        /* Add Product */
        String url = UriComponentsBuilder.fromUriString(ADD_PRODUCT_URL).build(VALID_CUSTOMER_ID,VALID_PRODUCT_ID).toString();
        ResponseEntity<String> addProductResponse = testRestTemplate.postForEntity(url,null, String.class);
        assertThat(addProductResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(addProductResponse.getHeaders().getContentType().toString()).startsWith(MediaType.TEXT_PLAIN_VALUE);
        assertThat(addProductResponse.getBody()).isEqualTo("SUCCESS");

        /* Get Basket and Verify */
        url = UriComponentsBuilder.fromUriString(BASKET_URL).build(VALID_CUSTOMER_ID).toString();
        ResponseEntity<Basket> basketResponse = testRestTemplate.getForEntity(url,Basket.class);
        assertThat(basketResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(basketResponse.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(basketResponse.getBody()).isNotNull();
        assertThat(basketResponse.getBody().getCustomerId()).isEqualTo(VALID_CUSTOMER_ID);
        assertThat(basketResponse.getBody().getProductMap()).hasSize(1);

        /* Add Second Product */
        url = UriComponentsBuilder.fromUriString(ADD_PRODUCT_URL).build(VALID_CUSTOMER_ID,VALID_PRODUCT_ID_1).toString();
        addProductResponse = testRestTemplate.postForEntity(url,null, String.class);
        assertThat(addProductResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        /* Verify Basket with Second Product */
        url = UriComponentsBuilder.fromUriString(BASKET_URL).build(VALID_CUSTOMER_ID).toString();
        basketResponse = testRestTemplate.getForEntity(url,Basket.class);
        assertThat(basketResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(basketResponse.getBody()).isNotNull();
        assertThat(basketResponse.getBody().getProductMap()).hasSize(2);
    }

    /* Basket Bad Tests*/
    @Test
    void basketNotFound() {
        String url = UriComponentsBuilder.fromUriString(BASKET_URL).build(NOTFOUND_CUSTOMER_ID).toString();
        ResponseEntity<Basket> basketResponse = testRestTemplate.getForEntity(url, Basket.class);
        assertThat(basketResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void basketInvalidCustomerId() {
        String url = UriComponentsBuilder.fromUriString(BASKET_URL).build(INVALID_CUSTOMER_ID).toString();
        ResponseEntity<Basket> basketResponse = testRestTemplate.getForEntity(url, Basket.class);
        assertThat(basketResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /* Add Product Bad Tests */
    @Test
    void addProductInvalidCustomerId() {
        String url = UriComponentsBuilder.fromUriString(ADD_PRODUCT_URL).build(INVALID_CUSTOMER_ID,VALID_PRODUCT_ID).toString();
        ResponseEntity<Basket> basketResponse = testRestTemplate.postForEntity(url,null, Basket.class);
        assertThat(basketResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void addProductInvalidProductId() {
        String url = UriComponentsBuilder.fromUriString(ADD_PRODUCT_URL).build(VALID_CUSTOMER_ID,INVALID_PRODUCT_ID).toString();
        ResponseEntity<Basket> basketResponse = testRestTemplate.postForEntity(url,null, Basket.class);
        assertThat(basketResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}