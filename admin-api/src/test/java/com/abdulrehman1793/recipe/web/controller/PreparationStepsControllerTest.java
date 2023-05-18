package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.services.PreparationStepsService;
import com.abdulrehman1793.recipe.web.models.request.PreparationStepRequest;
import com.abdulrehman1793.recipe.web.models.response.PreparationStepResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PreparationStepsController.class)
class PreparationStepsControllerTest {

    @MockBean
    private PreparationStepsService stepsService;

    @Autowired
    private MockMvc mockMvc;

    UUID uuid = UUID.randomUUID();

    @Test
    @DisplayName("findAllPrepStep-should return empty response")
    void findRecipePrepStepReturnEmpty() throws Exception {
        when(stepsService.findAllRecipePreparationSteps(uuid))
                .thenReturn(List.of());

        mockMvc.perform(get(PreparationStepsController.PREP_STEP_PATH + "/" + uuid))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(0)));

        verify(stepsService).findAllRecipePreparationSteps(uuid);
        verifyNoMoreInteractions(stepsService);
    }

    @Test
    @DisplayName("findAllPrepStep-should return response")
    void findRecipePrepStepsReturnResponse() throws Exception {
        when(stepsService.findAllRecipePreparationSteps(uuid))
                .thenReturn(List.of(PreparationStepResponse.builder().id(23L).build(),
                        PreparationStepResponse.builder().id(51L).build()));

        mockMvc.perform(get(PreparationStepsController.PREP_STEP_PATH + "/" + uuid))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)));

        verify(stepsService).findAllRecipePreparationSteps(uuid);
        verifyNoMoreInteractions(stepsService);
    }

    @Test
    @DisplayName("findAllPrepStep-should throw exception")
    void findRecipePrepStepsThrowsError() throws Exception {
        when(stepsService.findAllRecipePreparationSteps(uuid))
                .thenReturn(List.of());

        mockMvc.perform(get(PreparationStepsController.PREP_STEP_PATH + "/test"))
                .andExpect(status().is4xxClientError());

        verifyNoInteractions(stepsService);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            """
                    {
                        "title":" "
                    }
                    """,
            """
                    {
                        "title":null
                    }
                    """

    })
    @DisplayName("addPrepStep-should throw validation error")
    void addPrepStepValidationError(String request) throws Exception {
        mockMvc.perform(post(PreparationStepsController.PREP_STEP_PATH + "/" + uuid)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andDo(print());

        verifyNoInteractions(stepsService);
    }

    @Test
    void addPrepStepSuccess() throws Exception {
        PreparationStepRequest request = PreparationStepRequest.builder().title("cook").build();
        PreparationStepResponse response = PreparationStepResponse.builder()
                .id(1L).title("cook").build();

        when(stepsService.addRecipePreparationStep(uuid, request))
                .thenReturn(response);


        mockMvc.perform(post(PreparationStepsController.PREP_STEP_PATH + "/" + uuid)
                        .content("""
                                {
                                    "title":"cook"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(stepsService).addRecipePreparationStep(any(), any());
    }

    @Test
    void updatePrepStepInvalidPath() throws Exception {
        mockMvc.perform(put(PreparationStepsController.PREP_STEP_PATH + "/test")
                        .content("""
                                {
                                    "title":"cook"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message", containsString("Failed to convert")))
                .andDo(print());

        verifyNoInteractions(stepsService);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            """
                    {
                        "title":" "
                    }
                    """,
            """
                    {
                        "title":null
                    }
                    """

    })
    @DisplayName("addPrepStep-should throw validation error")
    void updateFail(String request) throws Exception {
        mockMvc.perform(put(PreparationStepsController.PREP_STEP_PATH + "/" + 1)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andDo(print());

        verifyNoInteractions(stepsService);
    }

    @Test
    void updatePrepStepSuccess() throws Exception {
        Long prepStepId = 3L;
        PreparationStepRequest request = PreparationStepRequest.builder().title("cook").build();

        when(stepsService.updateRecipePreparationStep(prepStepId, request))
                .thenReturn(PreparationStepResponse.builder()
                        .id(prepStepId)
                        .description("cook").build());

        mockMvc.perform(put(PreparationStepsController.PREP_STEP_PATH + "/" + prepStepId)
                        .content("""
                                {
                                    "title":"cook"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.parseInt(prepStepId.toString()))))
                .andExpect(jsonPath("$.title", is(request.getDescription())));

        verify(stepsService).updateRecipePreparationStep(prepStepId, request);
    }
}