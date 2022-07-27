package com.example.Items.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @Column(name = "id_item")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private Long category;

    @Column(name = "product")
    private Integer product;

    @Column(name = "is_supplier")
    private boolean isSupplier;

    private Integer amount;

    private Double price;
}
