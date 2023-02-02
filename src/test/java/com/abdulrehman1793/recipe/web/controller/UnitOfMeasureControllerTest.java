package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.services.UnitOfMeasureService;
import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(OrderAnnotation.class)
@WebMvcTest(UnitOfMeasureController.class)
class UnitOfMeasureControllerTest {
    @MockBean
    private UnitOfMeasureService unitOfMeasureService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("findAll-should return response on search")
    void findAllReturnResponseOnSearch() throws Exception {
        String keyword = "pinch";
        when(unitOfMeasureService.findAll(keyword)).thenReturn(
                List.of(new UnitOfMeasureResponse(9L, "pinch"),
                        new UnitOfMeasureResponse(31L, "two pinch")));

        mockMvc.perform(get("/uom").param("keyword", keyword)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)))
                .andDo(print());

        verify(unitOfMeasureService).findAll(keyword);
        verifyNoMoreInteractions(unitOfMeasureService);
    }

    @Test
    @Order(2)
    @DisplayName("findAll-should return all uom on blank/white space search")
    void findAllReturnAllResponseOnBlankSearch() throws Exception {
        String keyword = " ";
        when(unitOfMeasureService.findAll(keyword)).thenReturn(
                List.of(new UnitOfMeasureResponse(1L, "pinch"),
                        new UnitOfMeasureResponse(2L, "two pinch"),
                        new UnitOfMeasureResponse(3L, "teaspoon"),
                        new UnitOfMeasureResponse(4L, "uom")));

        mockMvc.perform(get("/uom").param("keyword", keyword)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(4)));

        verify(unitOfMeasureService).findAll(keyword);
        verifyNoMoreInteractions(unitOfMeasureService);
    }

    @Test
    @Order(3)
    @DisplayName("findAll-should return no response on invalid keyword")
    void findAllReturnNoResponseOnInvalidSearch() throws Exception {
        String keyword = " ";
        when(unitOfMeasureService.findAll(keyword)).thenReturn(List.of());

        mockMvc.perform(get("/uom").param("keyword", keyword)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(0)));

        verify(unitOfMeasureService).findAll(keyword);
        verifyNoMoreInteractions(unitOfMeasureService);
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(strings = {
            """
                    {
                    "uom":" "
                    }
                    """, """
            {
            "uom": null
            }
            """
    })
    @DisplayName("create- validation error")
    void create(String request) throws Exception {
        mockMvc.perform(post("/uom")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andDo(print());

        verifyNoInteractions(unitOfMeasureService);
    }

    @Test
    @Order(5)
    @DisplayName("create- save and return response")
    void createSuccess() throws Exception {
        String uomText = "teaspoon";
        when(unitOfMeasureService.create(uomText)).thenReturn(new UnitOfMeasureResponse(55L, uomText));

        mockMvc.perform(post("/uom")
                        .content("""
                                {
                                    "uom":"teaspoon"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(55)))
                .andExpect(jsonPath("$.uom", is(uomText)));

        verify(unitOfMeasureService).create(uomText);
    }


    @ParameterizedTest
    @Order(6)
    @ValueSource(strings = {
            """
                    {
                    "uom":" "
                    }
                    """, """
            {
            "uom": null
            }
            """
    })
    @DisplayName("update- validation error")
    void update(String request) throws Exception {
        mockMvc.perform(put("/uom/1")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andDo(print());

        verifyNoInteractions(unitOfMeasureService);
    }

    @Test
    @Order(7)
    @DisplayName("update-should raise exception on invalid path variable")
    void updateError() throws Exception {
        mockMvc.perform(put("/uom/test")
                        .content("""
                                {"uom":"teaspoon"}
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message", containsString("Failed to convert")))
                .andDo(print());

        verifyNoInteractions(unitOfMeasureService);
    }

    @Test
    @Order(8)
    @DisplayName("update- save and return response")
    void updateSuccess() throws Exception {
        Long uomId = 1L;
        String uomText = "teaspoon";
        when(unitOfMeasureService.update(uomId, uomText)).thenReturn(new UnitOfMeasureResponse(uomId, uomText));

        mockMvc.perform(put("/uom/" + uomId)
                        .content("""
                                {
                                    "uom":"teaspoon"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.parseInt(uomId.toString()))))
                .andExpect(jsonPath("$.uom", is(uomText)));

        verify(unitOfMeasureService).update(uomId, uomText);
    }


    @Test
    @Order(9)
    void deleteFail() throws Exception {
        mockMvc.perform(delete("/uom/test")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verifyNoInteractions(unitOfMeasureService);
    }

    @Test
    @Order(10)
    void deleteSuccess() throws Exception {
        Long uomId = 33L;
        doNothing().when(unitOfMeasureService).delete(uomId);

        mockMvc.perform(delete("/uom/" + uomId)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(unitOfMeasureService).delete(uomId);
    }
}