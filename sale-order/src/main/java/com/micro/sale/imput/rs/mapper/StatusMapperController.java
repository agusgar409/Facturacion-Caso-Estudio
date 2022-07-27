package com.micro.sale.imput.rs.mapper;


import com.micro.sale.domain.model.Status;
import com.micro.sale.imput.rs.request.StatusRequest;
import com.micro.sale.imput.rs.response.StatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author jrodriguez
 */
@Mapper
public interface StatusMapperController {

    Status toStatus(StatusRequest request);

    StatusResponse toStatusResponse(Status model);

    @Mapping(source = "id", target = "id")
    Status map(Long id);
}
