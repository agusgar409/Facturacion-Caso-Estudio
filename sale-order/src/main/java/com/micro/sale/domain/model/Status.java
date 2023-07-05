package com.micro.sale.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
/**
 * @author jrodriguez
 */
@Entity
@Table( name = "status")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusEnum name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return id.equals(status.id) && name.equals(status.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
