package com.example.Items.specification;


import com.example.Items.model.Item;
import com.example.Items.model.filter.OrderRequestFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderSpecification {


    public Specification<Item> getAllBySpec(OrderRequestFilter order) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (order.getIdProduct() != null) {
                predicates.add(criteriaBuilder.equal(root.get("product"), order.getIdProduct()));

            } else if (order.getIdCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), order.getIdCategory()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };


    }


}
