package com.abdulrehman1793.recipe.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UnitOfMeasureControllerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @SqlGroup(value =
            {@Sql(value = "classpath:scripts/INIT_UOM.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                    @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void findAll() {
        this.webTestClient.get()
                .uri(UnitOfMeasureController.UOM_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.size()").isEqualTo(5);
    }


    @Test
    @DisplayName("create-success")
    @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createSuccess() {
        String payload = """
                {
                    "uom":"new teaspoon"
                }
                """;

        this.webTestClient.post()
                .uri(UnitOfMeasureController.UOM_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @DisplayName("create-validation error")
    void createValidationError() {
        String payload = """
                {
                    "uom":" "
                }
                """;

        this.webTestClient.post()
                .uri(UnitOfMeasureController.UOM_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(422));
    }


    @Test
    @DisplayName("create-duplicate error")
    @SqlGroup(value = {@Sql(value = "classpath:scripts/INIT_UOM.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void createDuplicateError() {
        String payload = """
                {
                    "uom":"teaspoon"
                }
                """;

        this.webTestClient.post()
                .uri(UnitOfMeasureController.UOM_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    @SqlGroup(value = {@Sql(value = "classpath:scripts/INIT_UOM.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void update() {
        String payload = """
                {
                    "uom":"%s"
                }
                """;
        this.webTestClient.put()
                .uri(UnitOfMeasureController.UOM_PATH + "/" + 1)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload.formatted("updated teaspoon"))
                .exchange()
                .expectStatus().isOk();

        // should throw duplicate error -> uomId:1 already have uomText of "updated teaspoon"
        this.webTestClient.put()
                .uri(UnitOfMeasureController.UOM_PATH + "/" + 4)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload.formatted("updated teaspoon"))
                .exchange()
                .expectStatus().is4xxClientError();

        this.webTestClient.put()
                .uri(UnitOfMeasureController.UOM_PATH + "/" + 4)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload.formatted("ml"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @SqlGroup(value = {@Sql(value = "classpath:scripts/INIT_UOM.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void delete() {
        this.webTestClient.delete()
                .uri(UnitOfMeasureController.UOM_PATH + "/" + 4)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk();

        // should throw because uomId:4 already deleted
        this.webTestClient.delete()
                .uri(UnitOfMeasureController.UOM_PATH + "/" + 4)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is4xxClientError();
    }
}