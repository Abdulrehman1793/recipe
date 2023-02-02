package com.abdulrehman1793.recipe.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipe"}, callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties("recipe")
@Entity
public class PreparationSteps extends BaseEntity {
    @Builder
    public PreparationSteps(Long id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, int priority, String title, String description, Recipe recipe, Set<Images> images) {
        super(id, version, createdDate, lastModifiedDate);
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.recipe = recipe;
        this.images = images;
    }

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
