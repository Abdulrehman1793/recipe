package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.PreparationSteps;
import com.abdulrehman1793.recipe.domains.Recipe;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.PreparationStepsRepository;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.web.mappers.PreparationStepMapper;
import com.abdulrehman1793.recipe.web.models.request.PreparationStepRequest;
import com.abdulrehman1793.recipe.web.models.response.PreparationStepResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreparationStepsServiceImplTest {
    @Mock
    private PreparationStepsRepository stepsRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private PreparationStepMapper stepMapper;

    @InjectMocks
    private PreparationStepsServiceImpl cut;

    UUID uuid = UUID.randomUUID();

    @Test
    void notNull() {
        assertNotNull(stepsRepository);
        assertNotNull(recipeRepository);
        assertNotNull(stepMapper);
        assertNotNull(cut);
    }

    @Test
    @DisplayName("findAll-should throw on invalid recipe")
    void findAllThrowRecipeNotFound() {
        when(recipeRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> cut.findAllRecipePreparationSteps(uuid));

        verify(recipeRepository).findById(uuid);
        verifyNoInteractions(stepsRepository);
        verifyNoInteractions(stepMapper);
    }

    @Test
    @DisplayName("findAll-should return empty response")
    void findAllReturnEmptyResponse() {
        Recipe recipe = Recipe.builder().id(uuid).build();
        when(recipeRepository.findById(uuid)).thenReturn(Optional.of(recipe));

        when(stepsRepository.findAllByRecipe(recipe)).thenReturn(List.of());

        List<PreparationStepResponse> responses = cut.findAllRecipePreparationSteps(uuid);

        assertNotNull(responses);
        assertEquals(responses.size(), 0);

        verify(recipeRepository).findById(uuid);
        verify(stepsRepository).findAllByRecipe(recipe);
        verifyNoInteractions(stepMapper);
    }

    @Test
    @DisplayName("findAll-should return responses")
    void findAllReturnResponse() {
        Recipe recipe = Recipe.builder().id(uuid).build();
        when(recipeRepository.findById(uuid)).thenReturn(Optional.of(recipe));

        when(stepsRepository.findAllByRecipe(recipe))
                .thenReturn(List.of(
                        PreparationSteps.builder().id(1L).build(),
                        PreparationSteps.builder().id(2L).build(),
                        PreparationSteps.builder().id(3L).build()));

        List<PreparationStepResponse> responses = cut.findAllRecipePreparationSteps(uuid);

        assertNotNull(responses);
        assertEquals(responses.size(), 3);

        verify(recipeRepository).findById(uuid);
        verify(stepsRepository).findAllByRecipe(recipe);
        verify(stepMapper, times(3)).preparationStepToPreparationStepResponse(any());
    }

    @Test
    @DisplayName("add-should throw on invalid recipe")
    void addShouldThrowOnInvalidRecipe() {
        when(recipeRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class,
                () -> cut.addRecipePreparationStep(uuid, new PreparationStepRequest()));

        verify(recipeRepository).findById(uuid);
        verifyNoInteractions(stepsRepository);
        verifyNoInteractions(stepMapper);
    }

    @Test
    @DisplayName("add-should save & return")
    void addShouldReturn() {
        Recipe recipe = Recipe.builder().id(uuid).preparationSteps(Set.of()).build();
        PreparationStepRequest stepRequest = PreparationStepRequest.builder()
                .title("cook for 10 min").build();

        PreparationSteps steps = PreparationSteps.builder().build();
        PreparationSteps savedStep = PreparationSteps.builder().build();

        when(recipeRepository.findById(uuid)).thenReturn(Optional.of(recipe));
        when(stepMapper.preparationStepRequestToPreparationStep(stepRequest))
                .thenAnswer(invocation -> {
                    PreparationStepRequest argument = invocation.getArgument(0, PreparationStepRequest.class);
                    steps.setTitle(argument.getTitle());
                    return steps;
                });

        when(stepsRepository.save(steps)).thenAnswer(invocation -> {
            PreparationSteps argument = invocation.getArgument(0, PreparationSteps.class);
            savedStep.setId(1L);
            savedStep.setRecipe(recipe);
            savedStep.setTitle(argument.getTitle());
            return savedStep;
        });

        when(stepMapper.preparationStepToPreparationStepResponse(savedStep))
                .thenAnswer(invocation -> {
                    PreparationSteps argument = invocation.getArgument(0, PreparationSteps.class);
                    return PreparationStepResponse.builder()
                            .id(argument.getId())
                            .title(argument.getTitle())
                            .priority(argument.getPriority())
                            .build();
                });

        PreparationStepResponse response = cut.addRecipePreparationStep(uuid, stepRequest);

        assertNotNull(response);
        assertEquals(response.getTitle(), stepRequest.getTitle());

        InOrder inOrder = inOrder(recipeRepository, stepMapper, stepsRepository);

        inOrder.verify(recipeRepository).findById(uuid);
        inOrder.verify(stepMapper).preparationStepRequestToPreparationStep(stepRequest);
        inOrder.verify(stepsRepository).save(steps);
        inOrder.verify(stepMapper).preparationStepToPreparationStepResponse(savedStep);
    }

    @Test
    @DisplayName("update-should throw on invalid id")
    void updateShouldThrowOnInvalidId() {
        long stepId = 1L;
        when(stepsRepository.findById(stepId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class,
                () -> cut.updateRecipePreparationStep(stepId, PreparationStepRequest.builder().build()));

        verify(stepsRepository).findById(stepId);
        verifyNoInteractions(stepMapper);
    }

    @Test
    @DisplayName("update-should save and return")
    void updateShouldSaveAndReturn() {
        long stepId = 1L;
        PreparationStepRequest request = PreparationStepRequest.builder()
                .title("cook only for 10 minutes").description("Cook only on medium heat for 10 minutes")
                .build();

        PreparationSteps steps = PreparationSteps.builder().id(stepId)
                .title("Cook for 10 min").description("On medium low heat").build();

        PreparationSteps savedSteps = PreparationSteps.builder().build();

        when(stepsRepository.findById(stepId)).thenReturn(Optional.of(steps));

        when(stepsRepository.save(steps)).thenAnswer(invocation -> {
            PreparationSteps argument = invocation.getArgument(0, PreparationSteps.class);
            savedSteps.setId(argument.getId());
            savedSteps.setTitle(argument.getTitle());
            savedSteps.setDescription(argument.getDescription());
            return savedSteps;
        });

        when(stepMapper.preparationStepToPreparationStepResponse(savedSteps)).thenAnswer(invocation -> {
            PreparationSteps argument = invocation.getArgument(0, PreparationSteps.class);
            return PreparationStepResponse.builder()
                    .id(argument.getId())
                    .title(argument.getTitle())
                    .description(argument.getDescription())
                    .build();
        });

        PreparationStepResponse response = cut.updateRecipePreparationStep(stepId, request);

        assertNotNull(response);
        assertEquals(stepId, response.getId());
        assertEquals(response.getTitle(), request.getTitle());
        assertEquals(response.getDescription(), request.getDescription());

        verify(stepsRepository).findById(stepId);
        verify(stepMapper, never()).preparationStepRequestToPreparationStep(any());
        verify(stepsRepository).save(any());
        verify(stepMapper).preparationStepToPreparationStepResponse(any());
    }

}