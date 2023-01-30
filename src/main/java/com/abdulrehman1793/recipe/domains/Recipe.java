package com.abdulrehman1793.recipe.domains;

import com.abdulrehman1793.recipe.enums.Difficulty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Recipe extends BaseEntityUUID {
    @NotBlank
    private String title;

    private String subTitle;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<PreparationSteps> preparationSteps = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Images> images = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
    }

    public void addPreparationSteps(PreparationSteps preparationStep) {
        preparationStep.setRecipe(this);
        preparationSteps.add(preparationStep);
    }
}
