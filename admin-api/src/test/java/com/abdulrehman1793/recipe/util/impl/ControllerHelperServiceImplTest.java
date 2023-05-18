package com.abdulrehman1793.recipe.util.impl;

import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ControllerHelperServiceImplTest {

    ControllerHelperServiceImpl controllerHelperService;

    String[] fields = new String[]{"id", "uom"};
    String[] validSortParamWithValidDirection = new String[]{"id:asc", "uom:asc", "uom:desc"};

    @BeforeEach
    void setup() {
        controllerHelperService = new ControllerHelperServiceImpl();
    }

    @ParameterizedTest
    @ValueSource(strings = {"uom", "uom:12", "125", "12:34"})
    @DisplayName("should throw bad request exception on invalid param")
    void shouldThrowBadRequestOnInvalidParam(String param) {
        assertThrows(BadRequestException.class,
                () -> controllerHelperService.sortRequestParameterToSort(fields, new String[]{param}),
                "Expect to throw bad request exception");
    }

    @Test
    @DisplayName("should convert to Sort successfully")
    void shouldConvertToSortSuccessfully() {
        Sort sort = controllerHelperService.sortRequestParameterToSort(fields, validSortParamWithValidDirection);

        for (Order order : sort) {
            assertNotNull(order.getProperty());
            assertThat(order.getDirection()).isInstanceOf(Direction.class);
        }

//        Check for valid input param
        for (String s : validSortParamWithValidDirection) {
            assertThat(s).contains(":");
            assertThat(Direction.fromString(s.split(":")[1])).isInstanceOf(Direction.class);
        }
    }

    private static Stream<Arguments> validSortParamWithInvalidDirection() {
        return Stream.of(
                Arguments.of("id:dsc"),
                Arguments.of("id:ascending"),
                Arguments.of("id:descending")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"uom:ascending", "uom:dsc", "uom:descending"})
    @MethodSource(value = "validSortParamWithInvalidDirection")
    @DisplayName("should return Sort successfully")
    void shouldReturnDefaultDirectionOnConvertToSort(String param) {
        Sort sort = controllerHelperService.sortRequestParameterToSort(fields, new String[]{param});

        Order order = sort.iterator().next();
        assertNotNull(order.getProperty());
        assertThat(order.getDirection()).isInstanceOf(Direction.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"uom:ascend", "uom:descend", "uom:test", "id:random"})
    @DisplayName("should throw bad request exception on invalid direction")
    void shouldThrowBadRequestOnInvalidDirection(String param) {
        BadRequestException badRequestException = assertThrows(BadRequestException.class,
                () -> controllerHelperService.sortRequestParameterToSort(fields, new String[]{param}),
                "Expect to throw bad request exception");

        assertThat(badRequestException.getMessage()).contains(param.split(":")[1]);
    }


    @ParameterizedTest
    @ValueSource(strings = {"temp:asc", "invalid:desc","ttt:ascc","id:assc"})
    void shouldThrowOnInvalidSortField(String param) {
        BadRequestException badRequestException = assertThrows(BadRequestException.class,
                () -> controllerHelperService.sortRequestParameterToSort(fields, new String[]{param}),
                "Expect to throw bad request exception");
    }
}