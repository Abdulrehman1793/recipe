package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.Ingredient;
import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.IngredientRepository;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.repositories.UnitOfMeasureRepository;
import com.abdulrehman1793.recipe.web.mappers.IngredientMapper;
import com.abdulrehman1793.recipe.web.models.request.IngredientRequest;
import com.abdulrehman1793.recipe.web.models.response.IngredientResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class IngredientServiceImplTest {
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientMapper ingredientMapper;

    @InjectMocks
    IngredientServiceImpl cut;

    UUID uuid = UUID.randomUUID();

    @Test
    @DisplayName("findAll-should throw on invalid recipe id")
    void shouldThrowOnInvalidRecipeID() {
        when(recipeRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> cut.findAllRecipeIngredient(uuid));

        verify(recipeRepository).findById(uuid);
        verifyNoInteractions(ingredientRepository);
        verifyNoInteractions(ingredientMapper);
    }

    @Test
    @DisplayName("findAll-should return empty response successfully")
    void shouldReturnEmptyResult() {
        Recipe recipe = Recipe.builder().id(uuid).build();

        when(recipeRepository.findById(uuid)).thenReturn(Optional.of(recipe));

        when(ingredientRepository.findAllByRecipe(recipe)).thenReturn(List.of());

        List<IngredientResponse> responses = cut.findAllRecipeIngredient(uuid);

        assertNotNull(responses);
        assertEquals(responses.size(), 0);

        InOrder inOrder = inOrder(recipeRepository, ingredientRepository, ingredientMapper);

        inOrder.verify(recipeRepository).findById(any());
        inOrder.verify(ingredientRepository).findAllByRecipe(any());
        inOrder.verify(ingredientMapper, never()).ingredientToIngredientResponse(any());
    }

    @Test
    @DisplayName("findAll-should return response successfully")
    void shouldReturnResults() {
        Recipe recipe = Recipe.builder().id(uuid).build();

        when(recipeRepository.findById(uuid)).thenReturn(Optional.of(recipe));

        when(ingredientRepository.findAllByRecipe(recipe)).thenReturn(List.of(
                Ingredient.builder().id(1L).build(),
                Ingredient.builder().id(2L).build()
        ));

        List<IngredientResponse> responses = cut.findAllRecipeIngredient(uuid);

        assertNotNull(responses);
        assertEquals(responses.size(), 2);

        InOrder inOrder = inOrder(recipeRepository, ingredientRepository, ingredientMapper);

        inOrder.verify(recipeRepository).findById(any());
        inOrder.verify(ingredientRepository).findAllByRecipe(any());
        inOrder.verify(ingredientMapper, times(2)).ingredientToIngredientResponse(any());
        verifyNoMoreInteractions(ingredientMapper);

    }

    @Test
    @DisplayName("addIngredient-should throw on invalid recipe")
    void shouldThrowOnInvalidRecipe() {
        when(recipeRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> cut.addRecipeIngredient(uuid, IngredientRequest.builder().build()));

        verify(recipeRepository).findById(uuid);
        verifyNoInteractions(unitOfMeasureRepository);
        verifyNoInteractions(ingredientRepository);
        verifyNoInteractions(ingredientMapper);
    }

    @Test
    @DisplayName("addIngredient-should throw on invalid uom")
    void shouldThrowOnInvalidUOM() {
        when(recipeRepository.findById(uuid)).thenReturn(Optional.of(Recipe.builder().build()));

        when(unitOfMeasureRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class,
                () -> cut.addRecipeIngredient(uuid, IngredientRequest.builder().uomId(1L).build()));

        verify(recipeRepository).findById(uuid);
        verify(unitOfMeasureRepository).findById(anyLong());
        verifyNoInteractions(ingredientRepository);
        verifyNoInteractions(ingredientMapper);
    }

    @Test
    @DisplayName("addIngredient-should add & return response")
    void shouldAddAndReturn() {
        Recipe recipe = Recipe.builder().id(uuid).build();
        UnitOfMeasure uom = UnitOfMeasure.builder().id(9L).build();

        IngredientRequest request = IngredientRequest.builder().description("salt").uomId(9L).build();
        Ingredient requestToEntity = Ingredient.builder().description("salt")
                .unitOfMeasure(uom).recipe(recipe).build();

        when(recipeRepository.findById(uuid)).thenReturn(Optional.of(recipe));

        when(unitOfMeasureRepository.findById(9L)).thenReturn(Optional.of(uom));

        //TODO: stub
        when(ingredientRepository.save(any())).thenReturn(any());

        cut.addRecipeIngredient(uuid, request);

        verify(recipeRepository).findById(uuid);
        verify(unitOfMeasureRepository).findById(anyLong());
        verify(ingredientRepository).save(any());
    }

    @Test
    void updateRecipeIngredient() {
    }
}