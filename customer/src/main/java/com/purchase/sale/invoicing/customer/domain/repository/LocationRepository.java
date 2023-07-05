package com.purchase.sale.invoicing.customer.domain.repository;

import com.purchase.sale.invoicing.customer.domain.model.Location;
import com.purchase.sale.invoicing.customer.domain.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {


    List<Location> findByProvince (Province province);

    List<Location> findAllByProvince(Province idProvince);
}
