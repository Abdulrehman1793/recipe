package com.abdulrehman1793.recipe.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(exclude = {"recipe"}, callSuper = true)
@NoArgsConstructor
//@AllArgsConstructor
//@Builder(toBuilder = true)
@Entity
@JsonIgnoreProperties("recipe")
public class Ingredient extends BaseEntity {
    @Builder
    public Ingredient(Long id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String description, BigDecimal amount, Recipe recipe, UnitOfMeasure unitOfMeasure) {
        super(id, version, createdDate, lastModifiedDate);
        this.description = description;
        this.amount = amount;
        this.recipe = recipe;
        this.unitOfMeasure = unitOfMeasure;
    }

    private String description;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;
}
