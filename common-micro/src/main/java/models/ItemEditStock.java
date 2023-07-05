package models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ItemEditStock {

    private List<ProductRequest> listProducts;

    private boolean isSupplier;
}
