package com.example.purchase.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import request.ProductRequest;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class PurchaseOrderRequest {



    @JsonProperty("customer")
    private Long customer;

    @DateTimeFormat(pattern = "yyyy-M-d", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("status")
    private Long status;

    @JsonProperty("listProducts")
    private List<ProductRequest> listProducts;

    @JsonProperty("category")
    private Long category;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("isProveedor")
    private boolean isProveedor;
}
