package com.purchase.sale.invoicing.customer.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
/**
 * @author jrodriguez
 */
@Data
@Entity(name = "customer_type")
@Table
public class CustomerType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerType that = (CustomerType) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
