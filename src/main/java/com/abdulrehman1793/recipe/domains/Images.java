package com.abdulrehman1793.recipe.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties("recipe")
@Entity
public class Images extends BaseEntityUUID {
    @Lob
    private Byte[] image;

    @ManyToOne
    @JoinColumn(name = "preparation_steps_id")
    private PreparationSteps preparationSteps;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
