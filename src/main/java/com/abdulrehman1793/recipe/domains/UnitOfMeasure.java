package com.abdulrehman1793.recipe.domains;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
public class UnitOfMeasure extends BaseEntity {

    public UnitOfMeasure(String uom) {
        this.uom = uom;
    }

    @NotNull(message = "Unit of measurement is required")
    private String uom;
}
