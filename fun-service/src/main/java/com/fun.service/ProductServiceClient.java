package com.fun.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class ProductServiceClient {
    //TODO: Move to Config
    private final String baseUrl = "http://localhost:8081/api/v1";

    @Autowired
    private RestTemplate restTemplate;

    //TODO:Return Product after implementation
    public String getProduct(String productID) {
        String url = String.format("%s/%s/%s", baseUrl, "products", productID);
        try {
            return this.restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND || e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                String errMsg = "Product doesn't exist. Id: " + productID;
                throw new FunException(FunException.ErrorCode.PRODUCT_NOT_FOUND,errMsg,errMsg);
            } else {
                throw e;
            }
        }
    }
}
