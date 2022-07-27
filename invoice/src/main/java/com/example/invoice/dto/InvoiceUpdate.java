package com.example.invoice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class InvoiceUpdate {

    private String numberUpdate;

    private Long customerUpdate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d")
    private LocalDate dateUpdate;

    private Long statusUpdate;

    private Double totalUpdate;

    private String descriptionUpdate;

    private Integer categoryUpdate;
}
