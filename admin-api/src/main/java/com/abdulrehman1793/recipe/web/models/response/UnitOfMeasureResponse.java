package com.abdulrehman1793.recipe.web.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitOfMeasureResponse {
    private Long id;
    private String uom;
}
