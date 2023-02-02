package com.abdulrehman1793.recipe.domains;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public class UnitOfMeasure extends BaseEntity {

    @Builder
    public UnitOfMeasure(Long id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String uom) {
        super(id, version, createdDate, lastModifiedDate);
        this.uom = uom;
    }

    @NotBlank(message = "Unit of measurement is required")
    private String uom;
}
