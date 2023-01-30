package com.abdulrehman1793.recipe.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"recipe"}, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@JsonIgnoreProperties("recipe")
public class Ingredient extends BaseEntityUUID {

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
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
