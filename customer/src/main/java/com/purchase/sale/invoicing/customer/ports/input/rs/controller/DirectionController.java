package com.purchase.sale.invoicing.customer.ports.input.rs.controller;

import com.purchase.sale.invoicing.customer.domain.usecase.DirectionService;
import com.purchase.sale.invoicing.customer.ports.input.rs.api.ApiConstants;
import com.purchase.sale.invoicing.customer.ports.input.rs.mapper.DirectionMapper;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.LocationResponse;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.ProvinceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.Direction_URI)
public class DirectionController {
    private final DirectionService dirService;

    private final DirectionMapper directionMapper;

    //@PreAuthorize(ADMIN)

    /**
     * @throws FileNotFoundException Este metodo solo se ejecuta para actualizar la base de datos.
     */
    @PostMapping("/loadData")
    public void loadDirection() throws FileNotFoundException {
        dirService.load();
    }

    @GetMapping("/province")
    public ResponseEntity<List<ProvinceResponse>> getProvince() {
        List<ProvinceResponse> provinces = directionMapper.listModelToListProvinceResponse(dirService.getProvince());
        return ResponseEntity.ok().body(provinces);
    }

    @GetMapping("/location/{idProvince}")
    public ResponseEntity<List<LocationResponse>> getLocation(@PathVariable Integer idProvince) {

        List<LocationResponse> locations = directionMapper.listLocationsModelToLocationsResponse(dirService.getLocation(idProvince));

        return ResponseEntity.ok().body(locations);
    }

}
