package com.abdulrehman1793.recipe.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IngredientControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void findRecipeIngredients() {
    }

    @Test
    void addIngredient() {
    }

    @Test
    void updateIngredient() {
    }
}