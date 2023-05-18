package com.abdulrehman1793.recipe.controllers;

import com.abdulrehman1793.recipe.services.UnitOfMeasureService;
import com.abdulrehman1793.recipe.util.ControllerHelperService;
import com.abdulrehman1793.recipe.web.controller.UnitOfMeasureController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UnitOfMeasureController.class)
class UnitOfMeasureControllerTest {

    @MockBean
    private UnitOfMeasureService unitOfMeasureService;

    @MockBean
    private ControllerHelperService helperService;

    @Autowired
    private MockMvc mockMvc;

    String[] fields = new String[]{"uom"};

//    @Test
//    void shouldReturnResponse() throws Exception {
//        when(helperService.sortRequestParameterToSort(fields, new String[0]))
//                .thenReturn(Sort.by(desc("uom")));
//
//        when(unitOfMeasureService.findPage(any(Pageable.class)))
//                .thenReturn(Page.empty());
//
//        mockMvc.perform(
//                        get("/uom").header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.content.size()", is(0)))
//                .andDo(MockMvcResultHandlers.print());
//    }
}