package com.purchase.sale.invoicing.customer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author jrodriguez
 */
@Entity
@Table(name = "location")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {


    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_province")
    private Province province;

    @Column(name = "name")
    private String name;

}
