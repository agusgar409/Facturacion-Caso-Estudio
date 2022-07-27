package com.example.Items.model.filter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestFilter {

    private Integer idProduct;

    private Long idCategory;
}
