package com.abdulrehman1793.recipe.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Note extends BaseEntity{
    @Lob
    private String recipeNotes;

    @OneToOne
    private Recipe recipe;
}
