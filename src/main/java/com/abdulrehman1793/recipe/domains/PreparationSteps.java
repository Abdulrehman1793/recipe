package com.abdulrehman1793.recipe.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipe"}, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties("recipe")
@Entity
public class PreparationSteps extends BaseEntityUUID {
    private int priority;

    private String title;
    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preparationSteps")
    private Set<Images> images = new HashSet<>();
}
