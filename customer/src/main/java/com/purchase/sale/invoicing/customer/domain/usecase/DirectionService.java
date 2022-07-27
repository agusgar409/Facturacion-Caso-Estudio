package com.purchase.sale.invoicing.customer.domain.usecase;

import com.purchase.sale.invoicing.customer.domain.model.Location;
import com.purchase.sale.invoicing.customer.domain.model.Province;

import java.io.FileNotFoundException;
import java.util.List;

public interface DirectionService {

    void load() throws FileNotFoundException;

    List<Province> getProvince();

    List<Location> getLocation(Integer idProvince);
}
