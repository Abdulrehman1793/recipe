package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.services.IngredientService;
import com.abdulrehman1793.recipe.web.models.request.IngredientRequest;
import com.abdulrehman1793.recipe.web.models.response.IngredientResponse;
import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
class IngredientControllerTest {

    @MockBean
    private IngredientService ingredientService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("findAllIngredients-should return empty response")
    void findRecipeIngredientsReturnEmpty() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(ingredientService.findAllRecipeIngredient(uuid))
                .thenReturn(List.of());

        mockMvc.perform(get(IngredientController.INGREDIENT_PATH + "/" + uuid))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(0)));

        verify(ingredientService).findAllRecipeIngredient(uuid);
        verifyNoMoreInteractions(ingredientService);
    }

    @Test
    @DisplayName("findAllIngredients-should return response")
    void findRecipeIngredientsReturnResponse() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(ingredientService.findAllRecipeIngredient(uuid))
                .thenReturn(List.of(IngredientResponse.builder().id(23L).build(),
                        IngredientResponse.builder().id(51L).build()));

        mockMvc.perform(get(IngredientController.INGREDIENT_PATH + "/" + uuid))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)));

        verify(ingredientService).findAllRecipeIngredient(uuid);
        verifyNoMoreInteractions(ingredientService);
    }

    @Test
    @DisplayName("findAllIngredients-should throw exception")
    void findRecipeIngredientsThrowsError() throws Exception {
        when(ingredientService.findAllRecipeIngredient(UUID.randomUUID()))
                .thenReturn(List.of());

        mockMvc.perform(get(IngredientController.INGREDIENT_PATH + "/test"))
                .andExpect(status().is4xxClientError());

        verifyNoInteractions(ingredientService);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            """
                    {
                        "description":"salt"
                    }
                    """,
            """
                    {
                        "uomId": 2
                    }
                    """,
            """
                    {
                        "description":"salt",
                        "uomId": null
                    }
                    """,
            """
                    {
                        "description":" ",
                        "uomId": 45
                    }
                    """

    })
    @DisplayName("addIngredient-should throw validation error")
    void addIngredientValidationError(String request) throws Exception {
        mockMvc.perform(post(IngredientController.INGREDIENT_PATH + "/" + UUID.randomUUID())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andDo(print());

        verifyNoInteractions(ingredientService);
    }

    @Test
    void addIngredientSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        UnitOfMeasureResponse uom = new UnitOfMeasureResponse(3L, "teaspoon");
        IngredientRequest request = IngredientRequest.builder().uomId(3L).description("salt").build();
        IngredientResponse response = IngredientResponse.builder()
                .id(1L).unitOfMeasure(uom).description("salt").build();

        when(ingredientService.addRecipeIngredient(uuid, request))
                .thenReturn(response);


        mockMvc.perform(post(IngredientController.INGREDIENT_PATH + "/" + uuid)
                        .content("""
                                {
                                    "description":"salt",
                                    "uomId": 3
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ingredientService).addRecipeIngredient(any(), any());
    }

    @Test
    void updateIngredientInvalidPath() throws Exception {
        mockMvc.perform(put(IngredientController.INGREDIENT_PATH + "/test")
                        .content("""
                                {
                                    "description":"salt",
                                    "uomId": 3
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message", containsString("Failed to convert")))
                .andDo(print());

        verifyNoInteractions(ingredientService);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            """
                    {
                        "description":"salt"
                    }
                    """,
            """
                    {
                        "uomId": 2
                    }
                    """,
            """
                    {
                        "description":"salt",
                        "uomId": null
                    }
                    """,
            """
                    {
                        "description":" ",
                        "uomId": 45
                    }
                    """

    })
    @DisplayName("addIngredient-should throw validation error")
    void updateFail(String request) throws Exception {
        mockMvc.perform(put(IngredientController.INGREDIENT_PATH + "/" + 1)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andDo(print());

        verifyNoInteractions(ingredientService);
    }

    @Test
    void updateIngredientSuccess() throws Exception {
        Long ingredientId = 3L;
        IngredientRequest request = IngredientRequest.builder().description("salt").uomId(12L).build();

        when(ingredientService.updateRecipeIngredient(ingredientId, request))
                .thenReturn(IngredientResponse.builder()
                        .id(ingredientId)
                        .description("salt").unitOfMeasure(new UnitOfMeasureResponse(12L, "pinch")).build());

        mockMvc.perform(put(IngredientController.INGREDIENT_PATH + "/" + ingredientId)
                        .content("""
                                {
                                    "description":"salt",
                                    "uomId":12
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.parseInt(ingredientId.toString()))))
                .andExpect(jsonPath("$.unitOfMeasure.id", is(Integer.parseInt(request.getUomId().toString()))))
                .andExpect(jsonPath("$.description", is(request.getDescription())));

        verify(ingredientService).updateRecipeIngredient(ingredientId, request);
    }
}