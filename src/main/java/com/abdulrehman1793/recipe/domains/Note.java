package com.abdulrehman1793.recipe.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"}, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties("recipe")
@Entity
public class Note extends BaseEntityUUID {
    @Lob
    private String recipeNotes;

    @OneToOne
    private Recipe recipe;
}
