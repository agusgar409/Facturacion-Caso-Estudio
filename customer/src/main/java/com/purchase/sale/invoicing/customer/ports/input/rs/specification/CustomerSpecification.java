package com.purchase.sale.invoicing.customer.ports.input.rs.specification;

import com.purchase.sale.invoicing.customer.domain.model.Customer;
import com.purchase.sale.invoicing.customer.domain.model.CustomerType;
import com.purchase.sale.invoicing.customer.domain.model.Direction;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerRequestFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jrodriguez
 */
@Component
public class CustomerSpecification {

    public Specification<Customer> getAllSpecification(CustomerRequestFilter customer) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Customer, Direction> joinDirection = root.join("direction");

            if (customer.getDni() != null) {
                predicates.add(criteriaBuilder.equal(root.get("dni"), customer.getDni()));
            }

            if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + customer.getEmail() + "%"));
            }

            if (customer.getName() != null && !customer.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + customer.getName().toUpperCase() + "%"));
            }

            if (customer.getLastname() != null && !customer.getLastname().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("lastname"), "%" + customer.getLastname().toUpperCase() + "%"));
            }

            if (customer.getProvinceName() != null) {
                predicates.add(criteriaBuilder.like(joinDirection.get("province").get("name"), "%" + customer.getProvinceName() + "%"));
            }

            if (customer.getLocationName() != null) {
                predicates.add(criteriaBuilder.equal(joinDirection.get("location").get("name"), "%" + customer.getLocationName() + "%"));
            }

            if (customer.getIdType() != null) {
                Join<Customer, CustomerType> joinType = root.join("type");
                predicates.add(criteriaBuilder.equal(joinType.get("id"), customer.getIdType()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
