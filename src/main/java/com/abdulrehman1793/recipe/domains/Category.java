package com.abdulrehman1793.recipe.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties("recipes")
@Entity
public class Category extends BaseEntity {
    @Builder
    public Category(Long id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String title, String description, Set<Recipe> recipes) {
        super(id, version, createdDate, lastModifiedDate);
        this.title = title;
        this.description = description;
        this.recipes = recipes;
    }

    @NotNull
    private String title;

    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<>();
}
