package com.example.invoice.model.filter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestFilter {

    private String number;

    private Long customer;

    private Long status;

    private Integer idCategory;
}
