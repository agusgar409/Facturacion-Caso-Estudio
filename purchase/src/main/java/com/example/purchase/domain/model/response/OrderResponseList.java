package com.example.purchase.domain.model.response;

import com.example.Items.model.response.OrderResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseList {

    @JsonProperty("content")
    private List<PurchaseResponse> content = null;

    @JsonProperty("nextUri")
    private String nextUri;

    @JsonProperty("previousUri")
    private String previousUri;

    @JsonProperty("totalPages")
    private Integer totalPages;

    @JsonProperty("totalElements")
    private Long totalElements;

}
