package com.example.invoice.specification;


import com.example.invoice.model.InvoiceEntity;
import com.example.invoice.model.filter.OrderRequestFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderSpecification {


    public Specification<InvoiceEntity> getAllBySpec(OrderRequestFilter order) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (order.getNumber() != null) {
                predicates.add(criteriaBuilder.like(root.get("number"), order.getNumber()));

            } else if (order.getCustomer() != null) {
                predicates.add(criteriaBuilder.equal(root.get("customer"), order.getCustomer()));

            } else if (order.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), order.getStatus()));

            } else if (order.getIdCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("idCategory"), order.getIdCategory()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };


    }


}
