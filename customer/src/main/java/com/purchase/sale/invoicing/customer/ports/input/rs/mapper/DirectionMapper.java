package com.purchase.sale.invoicing.customer.ports.input.rs.mapper;

import com.purchase.sale.invoicing.customer.domain.model.Direction;
import com.purchase.sale.invoicing.customer.domain.model.Location;
import com.purchase.sale.invoicing.customer.domain.model.Province;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.DirectionRequest;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.DirectionUpdateRequest;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.LocationRequest;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.ProvinceRequest;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.DirectionResponse;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.LocationResponse;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.ProvinceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author jrodriguez
 */
@Mapper
public interface DirectionMapper {

    @Mapping(source = "province", target = "province")
    @Mapping(source = "location", target  = "location")
    Direction toDirectionModel(DirectionRequest request);

    Province toProvinceModel(ProvinceRequest request);
    ProvinceResponse toProvinceResponse (Province model);

    Location toLocationModel(LocationRequest request);
    LocationResponse toLocationResponse(Location model);


    @Mapping(source = "province", target = "province")
    @Mapping(source = "location", target  = "location")
    Direction updateToDirectionModel(DirectionUpdateRequest request);

    @Mapping(source = "province", target = "province")
    @Mapping(source = "location", target  = "location")
    DirectionResponse toDirectionResponse(Direction model);

    List<ProvinceResponse> listModelToListProvinceResponse  (List<Province> listModel );

    List<LocationResponse> listLocationsModelToLocationsResponse  (List<Location> listModel);

}
