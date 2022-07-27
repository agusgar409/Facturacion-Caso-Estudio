package com.example.Items.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    @JsonProperty("is_supplier")
    private boolean isSupplier;

    @JsonProperty("category")
    private Long category;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("product")
    private Integer product;

    @JsonProperty("price")
    private Double price;


}
