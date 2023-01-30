package com.abdulrehman1793.recipe.controllers;

import com.abdulrehman1793.recipe.services.UnitOfMeasureService;
import com.abdulrehman1793.recipe.util.ControllerHelperService;
import com.abdulrehman1793.recipe.web.controller.UnitOfMeasureController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UnitOfMeasureController.class)
class UnitOfMeasureControllerTest {

    @MockBean
    private UnitOfMeasureService unitOfMeasureService;

    @MockBean
    private ControllerHelperService helperService;

    @Autowired
    private MockMvc mockMvc;

    String[] fields = new String[]{"uom"};

    @Test
    void shouldReturnResponse() throws Exception {
        when(helperService.sortRequestParameterToSort(fields, new String[0]))
                .thenReturn(Sort.by(desc("uom")));

        when(unitOfMeasureService.findPage(any(Pageable.class)))
                .thenReturn(Page.empty());

        mockMvc.perform(
                        get("/uom").header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()", is(0)))
                .andDo(MockMvcResultHandlers.print());
    }
}