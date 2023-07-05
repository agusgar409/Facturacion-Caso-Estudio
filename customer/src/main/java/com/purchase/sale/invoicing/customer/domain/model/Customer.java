package com.purchase.sale.invoicing.customer.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * @author jrodriguez
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "customer")
@Table
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dni", nullable = false)
    private Long dni;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_direction")
    private Direction direction;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "customer_customer_type",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"))
    private Set<CustomerType> type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) && dni.equals(customer.dni) && email.equals(customer.email) && name.equals(customer.name) && lastname.equals(customer.lastname) && direction.equals(customer.direction) && type.equals(customer.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
