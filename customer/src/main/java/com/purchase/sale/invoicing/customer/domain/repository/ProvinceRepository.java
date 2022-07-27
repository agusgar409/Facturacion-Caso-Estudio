package com.purchase.sale.invoicing.customer.domain.repository;

import com.purchase.sale.invoicing.customer.domain.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    Province findByName(String name);
}
