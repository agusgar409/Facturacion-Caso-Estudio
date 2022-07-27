package util.models;

import lombok.*;
import request.ProductRequest;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ItemEditStock {

    private List<ProductRequest> listProducts;

    private boolean isProveedor;
}
