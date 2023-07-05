package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
