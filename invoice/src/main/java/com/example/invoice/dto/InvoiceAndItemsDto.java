package com.example.invoice.dto;

import com.example.invoice.model.InvoiceItems;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class InvoiceAndItemsDto {

    private Long idInvoice;

    private String number;

    private Long customer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d")
    private LocalDate date;

    private Long status;

    private String description;

    private Integer category;

    private List<Long> idItems;
}
