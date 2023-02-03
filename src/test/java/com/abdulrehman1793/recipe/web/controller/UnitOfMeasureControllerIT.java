package com.abdulrehman1793.recipe.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UnitOfMeasureControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Sql("classpath:scripts/INIT_UOM.sql")
    void findAll() {
        this.webTestClient.get()
                .uri(UnitOfMeasureController.UOM_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.size()").isEqualTo(5);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}