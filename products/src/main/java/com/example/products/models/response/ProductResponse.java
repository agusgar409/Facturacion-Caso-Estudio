package com.example.products.models.response;


import com.example.products.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {

    private Integer id;

    private Integer amount;

    private Double price;

    private String name;

    private String description;

}
