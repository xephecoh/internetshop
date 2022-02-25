package com.khamutov.web;


import com.khamutov.entities.Product;

public class RequestBodyParser {
    public RequestBodyParser() {
    }

    final String[] requestValues = new String[5];

    public RequestBodyParser parseRequest(String requestBody) {
        int counter = 0;
        String[] fieldValue = requestBody.split("&");
        for (String el : fieldValue) {
            String[] split = el.split("=");
            String value = split[1];
            requestValues[counter] = value;
            counter++;
        }
        return this;
    }

    public Product getProduct() {
        Product product = new Product();
        product.setName(requestValues[0]);
        product.setPrice(Integer.parseInt(requestValues[1]));
        product.setId(Integer.parseInt(requestValues[2]));
        return product;
    }
}
