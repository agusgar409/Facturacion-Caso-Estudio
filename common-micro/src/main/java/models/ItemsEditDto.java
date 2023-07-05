package models;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemsEditDto {

    private List<Long> listItems;

    private boolean isSupplier;
}
