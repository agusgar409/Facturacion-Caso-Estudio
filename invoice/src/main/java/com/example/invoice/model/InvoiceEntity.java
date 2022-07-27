package com.example.invoice.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice")
    private Long idInvoice;

    @Column(name = "number")
    private String number;

    @Column(name = "dni_customer")
    private Long customer;

    @CreationTimestamp
    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d")
    private LocalDate date;

    @Column(name = "status")
    private Long status;

    @Column(name = "total_price")
    private Double total;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private Integer category;
}
