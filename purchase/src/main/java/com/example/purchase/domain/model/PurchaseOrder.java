package com.example.purchase.domain.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Table(name = "purchase_order")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "customer")
    private Long customer;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-M-d", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;

    @Column(name = "status")
    private Long status;

    @Column(name = "category")
    private Integer category;


    @Column(name = "total")
    private Double total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrder purchaseOrder = (PurchaseOrder) o;
        return Objects.equals(number, purchaseOrder.number)
                && Objects.equals(customer, purchaseOrder.customer)
                && Objects.equals(date, purchaseOrder.date)
                && Objects.equals(status, purchaseOrder.status)
                && Objects.equals(category, purchaseOrder.category)
                && Objects.equals(total, purchaseOrder.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
