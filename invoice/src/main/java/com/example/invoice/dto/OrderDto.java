package com.example.invoice.dto;

import com.example.invoice.model.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class OrderDto {

    @JsonProperty("number")
    private Integer number;

    @JsonProperty("customer")
    private Long customer;

    @JsonProperty("date")
    @DateTimeFormat(pattern = "yyyy-M-d")
    private LocalDate date;

    @JsonProperty("status")
    private Long status;

    @JsonProperty("listProducts")
    private List<ProductResponse> listProducts;

    @JsonProperty("category")
    private Long idCategory;

    @JsonProperty("total")
    private Double total;
}
