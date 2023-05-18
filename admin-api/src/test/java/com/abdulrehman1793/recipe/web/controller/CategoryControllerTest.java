package com.abdulrehman1793.recipe.web.controller;

import com.abdulrehman1793.recipe.services.CategoryService;
import com.abdulrehman1793.recipe.web.models.request.CategoryRequest;
import com.abdulrehman1793.recipe.web.models.response.CategoryResponse;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("findAll-should return all category on empty keyword")
    void findAllShouldReturnAllOnEmptyKeyword() throws Exception {
        String keyword = "";
        when(categoryService.findAll(keyword))
                .thenReturn(List.of(CategoryResponse.builder().id(1L).build(),
                        CategoryResponse.builder().id(2L).build(),
                        CategoryResponse.builder().id(3L).build()));

        mockMvc.perform(get(CategoryController.CATEGORY_PATH).param("keyword", keyword)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(3)));

        verify(categoryService).findAll(keyword);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("findAll-should return all category on no param")
    void findAllShouldReturnAllOnNoParam() throws Exception {

        when(categoryService.findAll(null))
                .thenReturn(List.of(CategoryResponse.builder().id(1L).build(),
                        CategoryResponse.builder().id(2L).build(),
                        CategoryResponse.builder().id(3L).build()));

        mockMvc.perform(get(CategoryController.CATEGORY_PATH)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(3)));

        verify(categoryService).findAll(null);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("findAll-should return filter category on keyword")
    void findAllShouldReturnFilterResult() throws Exception {
        String keyword = "in";
        when(categoryService.findAll(keyword))
                .thenReturn(List.of(CategoryResponse.builder().id(3L).title("Indonesian").build(),
                        CategoryResponse.builder().id(21L).title("Indian").build()));

        mockMvc.perform(get(CategoryController.CATEGORY_PATH).param("keyword", keyword)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)));

        verify(categoryService).findAll(keyword);
        verifyNoMoreInteractions(categoryService);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            """
                    {
                    "title":" "
                    }
                    """, """
            {
            "title": null
            }
            """
    })
    @DisplayName("create-should throw validation error")
    void createValidationError(String request) throws Exception {
        mockMvc.perform(post(CategoryController.CATEGORY_PATH)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andDo(print());

        verifyNoInteractions(categoryService);
    }

    @Test
    @Order(5)
    @DisplayName("create- save category and return response")
    void createSuccess() throws Exception {
        String title = "indian";
        CategoryRequest request = CategoryRequest.builder().title(title).build();
        when(categoryService.create(request))
                .thenReturn(CategoryResponse.builder().id(5L).title(title).build());

        mockMvc.perform(post(CategoryController.CATEGORY_PATH)
                        .content("""
                                {
                                    "title":"indian"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.description").value(IsNull.nullValue()));

        verify(categoryService).create(request);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            """
                    {
                    "title":" "
                    }
                    """, """
            {
            "title": null
            }
            """
    })
    @DisplayName("update- validation error")
    void update(String request) throws Exception {
        mockMvc.perform(put(CategoryController.CATEGORY_PATH + "/1")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andDo(print());

        verifyNoInteractions(categoryService);
    }

    @Test
    @DisplayName("update-should throw exception on invalid path variable")
    void updateError() throws Exception {
        mockMvc.perform(put(CategoryController.CATEGORY_PATH + "/test")
                        .content("""
                                {"title":"indian"}
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message", containsString("Failed to convert")))
                .andDo(print());

        verifyNoInteractions(categoryService);
    }

    @Test
    @DisplayName("update- save and return response")
    void updateSuccess() throws Exception {
        Long categoryId = 1L;
        CategoryRequest request = CategoryRequest.builder().title("indian cuisine").build();
        CategoryResponse response = CategoryResponse.builder().id(categoryId).title("indian cuisine").build();
        when(categoryService.update(categoryId, request))
                .thenReturn(response);

        mockMvc.perform(put(CategoryController.CATEGORY_PATH + "/" + categoryId)
                        .content("""
                                {
                                    "title":"indian cuisine"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.parseInt(categoryId.toString()))))
                .andExpect(jsonPath("$.title", is(request.getTitle())))
                .andExpect(jsonPath("$.description", is(request.getDescription())));

        verify(categoryService).update(categoryId, request);
    }

    @Test
    void deleteFail() throws Exception {
        mockMvc.perform(delete(CategoryController.CATEGORY_PATH + "/test")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verifyNoInteractions(categoryService);
    }

    @Test
    void deleteSuccess() throws Exception {
        Long categoryId = 33L;
        doNothing().when(categoryService).delete(categoryId);

        mockMvc.perform(delete(CategoryController.CATEGORY_PATH + "/" + categoryId)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(categoryService).delete(categoryId);
    }
}