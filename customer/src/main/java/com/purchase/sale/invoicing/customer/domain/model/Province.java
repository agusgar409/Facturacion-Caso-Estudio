package com.purchase.sale.invoicing.customer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jrodriguez
 */
@Entity
@Table(name = "province")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Province {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;


}
