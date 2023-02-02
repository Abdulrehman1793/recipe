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
import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;
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

        //TODO: stub object of Ingredient to service -> any() to requestToEntity
        when(ingredientRepository.save(any())).thenReturn(any());

        cut.addRecipeIngredient(uuid, request);

        verify(recipeRepository).findById(uuid);
        verify(unitOfMeasureRepository).findById(anyLong());
        verify(ingredientRepository).save(any());
    }

    @Test
    @DisplayName("updateIngredient-should throw on invalid ingredient id")
    void shouldThrowOnInvalidIngredientId() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class,
                () -> cut.updateRecipeIngredient(1L, IngredientRequest.builder().build()));

        verify(ingredientRepository).findById(any());
        verifyNoInteractions(unitOfMeasureRepository);
        verifyNoMoreInteractions(ingredientRepository);
        verifyNoInteractions(ingredientMapper);
    }

    @Test
    @DisplayName("updateIngredient-should throw on invalid uom")
    void shouldThrowOnInvalidUOMOnUpdate() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(Ingredient.builder().build()));

        when(unitOfMeasureRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class,
                () -> cut.updateRecipeIngredient(1L, IngredientRequest.builder().uomId(1L).build()));

        verify(ingredientRepository).findById(any());
        verify(unitOfMeasureRepository).findById(anyLong());
        verifyNoMoreInteractions(ingredientRepository);
        verifyNoInteractions(ingredientMapper);
    }

    @Test
    @DisplayName("updateIngredient-should update & return")
    void shouldUpdateAndReturn() {
        Long ingredientId = 63L;
        Long uomId = 42L;

        UnitOfMeasure uom = UnitOfMeasure.builder().id(uomId).build();

        Ingredient ingredient = Ingredient.builder()
                .description("salt")
                .id(ingredientId)
                .unitOfMeasure(uom).build();


        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        when(unitOfMeasureRepository.findById(uomId)).thenReturn(Optional.of(uom));

        when(ingredientRepository.save(ingredient))
                .thenReturn(ingredient);

        when(ingredientMapper.ingredientToIngredientResponse(ingredient)).thenAnswer(invocation -> {
            Ingredient savedIngredient = invocation.getArgument(0, Ingredient.class);
            UnitOfMeasure savedUOM = savedIngredient.getUnitOfMeasure();
            return IngredientResponse.builder()
                    .unitOfMeasure(
                            new UnitOfMeasureResponse(savedUOM.getId(), savedUOM.getUom()))
                    .description(savedIngredient.getDescription())
                    .id(savedIngredient.getId())
                    .build();
        });


        IngredientResponse response = cut.updateRecipeIngredient(ingredientId,
                IngredientRequest.builder().description("New salt").uomId(uomId).build());

        assertNotNull(response);
        assertEquals(response.getDescription(), "New salt");

        InOrder inOrder = inOrder(ingredientRepository, unitOfMeasureRepository, ingredientMapper);

        inOrder.verify(ingredientRepository).findById(any());
        inOrder.verify(unitOfMeasureRepository).findById(anyLong());
        inOrder.verify(ingredientRepository).save(any(Ingredient.class));
        inOrder.verify(ingredientMapper).ingredientToIngredientResponse(any(Ingredient.class));
    }
}