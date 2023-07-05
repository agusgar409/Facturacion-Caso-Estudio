package com.micro.sale.imput.rs.mapper;

import com.micro.sale.domain.model.Status;
import com.micro.sale.imput.rs.request.StatusRequest;
import com.micro.sale.imput.rs.response.StatusResponse;
import com.micro.sale.imput.rs.response.StatusResponse.StatusResponseBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class StatusMapperControllerImpl implements StatusMapperController {

    @Override
    public Status toStatus(StatusRequest request) {
        if ( request == null ) {
            return null;
        }

        Status status = new Status();

        status.setId( request.getId() );

        return status;
    }

    @Override
    public StatusResponse toStatusResponse(Status model) {
        if ( model == null ) {
            return null;
        }

        StatusResponseBuilder statusResponse = StatusResponse.builder();

        statusResponse.id( model.getId() );
        if ( model.getName() != null ) {
            statusResponse.name( model.getName().name() );
        }

        return statusResponse.build();
    }

    @Override
    public Status map(Long id) {
        if ( id == null ) {
            return null;
        }

        Status status = new Status();

        status.setId( id );

        return status;
    }
}
