package com.purchase.sale.invoicing.customer.domain.usecase.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.purchase.sale.invoicing.customer.domain.model.Location;
import com.purchase.sale.invoicing.customer.domain.model.Province;
import com.purchase.sale.invoicing.customer.domain.repository.LocationRepository;
import com.purchase.sale.invoicing.customer.domain.repository.ProvinceRepository;
import com.purchase.sale.invoicing.customer.domain.usecase.DirectionService;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.ProvinceResponse;
import errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * @author jrodriguez
 */
@Service
@RequiredArgsConstructor
public class DirectionServiceImpl implements DirectionService {
    private final ProvinceRepository provinceRepo;
    private final LocationRepository locationRepo;


    @Override
    public void load() throws FileNotFoundException {
        String provJSON = "C:\\Users\\jrodriguez\\Desktop\\capacitacion\\Facturacion-Caso-Estudio\\customer\\src\\main\\resources\\directionsJSON\\provinces.json";
        String locationJSON = "C:\\Users\\jrodriguez\\Desktop\\capacitacion\\Facturacion-Caso-Estudio\\customer\\src\\main\\resources\\directionsJSON\\location.json";

        loadProvinces(provJSON);
        loadLocation(locationJSON);

    }

    @Override
    public List<Province> getProvince() {
        return provinceRepo.findAll();
    }

    @Override
    public List<Location> getLocation(Integer idProvince) {
        Province province = provinceRepo.findById(idProvince).orElseThrow();

        return locationRepo.findByProvince(province);
    }

    private void loadLocation(String locationJSON) throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(new FileReader(locationJSON));
        JsonObject object = parser.parse(String.valueOf(element)).getAsJsonObject();
        JsonArray arrProv = object.get("localidades").getAsJsonArray();

        arrProv.forEach(p -> {
            JsonObject object1 = p.getAsJsonObject();

            Long idLocation = object1.get("id").getAsLong();
            String name = object1.get("nombre").getAsString();
            JsonObject prov = object1.get("provincia").getAsJsonObject();
            Integer idProv = prov.get("id").getAsInt();
            Province province = provinceRepo.findById(idProv).orElseThrow(()-> new NotFoundException(idProv));

            Location location = new Location(idLocation, province, name);
            locationRepo.save(location);
        });


    }

    private void loadProvinces(String provJSON) throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(new FileReader(provJSON));
        JsonObject object = parser.parse(String.valueOf(element)).getAsJsonObject();
        JsonArray arrProv = object.get("provincias").getAsJsonArray();

        arrProv.forEach(p -> {
            JsonObject object1 = p.getAsJsonObject();

            Integer id = object1.get("id").getAsInt();
            String name = object1.get("nombre").getAsString();

            Province province = new Province(id, name);
            provinceRepo.save(province);
        });
    }


}
