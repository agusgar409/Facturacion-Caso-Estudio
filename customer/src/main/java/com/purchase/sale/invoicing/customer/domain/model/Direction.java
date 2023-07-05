package com.purchase.sale.invoicing.customer.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author jrodriguez
 */

@Data
@Entity(name = "direction")
@Table
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "height", nullable = false)
    private Integer height;

    @OneToOne
    @JoinColumn(name = "id_province", nullable = false)
    private Province province;

    @OneToOne
    @JoinColumn(name = "id_location")
    private Location location;

    @Column(name = "postal_code", nullable = false)
    private int postalCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return postalCode == direction.postalCode
                && id.equals(direction.id)
                && street.equals(direction.street)
                && height.equals(direction.height)
                && province.equals(direction.province);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
