package com.example.products.imput.rs.response;


import lombok.*;

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
