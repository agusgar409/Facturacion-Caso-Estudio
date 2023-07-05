package com.example.purchase.domain.specification;



import com.example.purchase.domain.model.PurchaseOrder;
import com.example.purchase.domain.model.request.OrderRequestFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderSpecification {


    public Specification<PurchaseOrder> getAllBySpec(OrderRequestFilter order) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (order.getIdCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), order.getIdCategory()));

            } else if (order.getCustomer() != null) {
                predicates.add(criteriaBuilder.equal(root.get("Customer"), order.getCustomer()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
