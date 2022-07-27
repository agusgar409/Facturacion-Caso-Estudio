package com.example.Items.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import request.ProductRequest;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ItemRequest {

    @JsonProperty("number")
    private Long IdNumber;

    @JsonProperty("customer")
    private Long customer;

    @DateTimeFormat
    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("status")
    private Long status;

    @JsonProperty("listProducts")
    private List<ProductRequest> listProducts;

    /**
     * id = 1 -> compra
     * id = 2 -> venta
     * id = 3 -> facturacion
     */
    @JsonProperty("category")
    private Integer idCategory;

    @JsonProperty("total")
    private Double totalPrice;

}
