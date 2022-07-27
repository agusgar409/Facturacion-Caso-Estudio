package com.example.invoice.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class InvoiceDto {

    private Long idInvoice;

    private String number;

    private Long customer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d")
    private LocalDate date;

    private Long status;

    private String description;

    private Integer category;

}
