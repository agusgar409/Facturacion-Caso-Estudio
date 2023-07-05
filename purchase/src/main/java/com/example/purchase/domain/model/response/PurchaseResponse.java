package com.example.purchase.domain.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * The type Purchase dto.
 */
@RequiredArgsConstructor
@Getter @Setter
public class PurchaseResponse {

    @JsonProperty("number")
    private String number;

    @NotNull
    @JsonProperty("customer")
    private Long customer;

    /**
     * id = 1 -> compra
     * id = 2 -> venta
     * id = 3 -> facturacion
     */
    @NotNull
    @JsonProperty("category")
    private Long category;

    @NotNull
    @JsonProperty("total")
    private Double total;

    @NotNull
    @JsonFormat(pattern = "yyyy-M-d")
    @DateTimeFormat(pattern = "yyyy-M-d", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;

    @NotNull
    @JsonProperty("status")
    private Long status;
}
