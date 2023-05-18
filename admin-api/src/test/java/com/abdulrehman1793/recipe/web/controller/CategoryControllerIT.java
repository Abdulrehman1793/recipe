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
class CategoryControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @SqlGroup(value =
            {@Sql(value = "classpath:scripts/INIT_Category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                    @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void findAll() {
        this.webTestClient.get()
                .uri(CategoryController.CATEGORY_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.size()").isEqualTo(5);
    }

    @Test
    @SqlGroup(value =
            {@Sql(value = "classpath:scripts/INIT_Category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                    @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void findAllFiltered() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(CategoryController.CATEGORY_PATH)
                        .queryParam("keyword", "pot")
                        .build())
                .header(ACCEPT, APPLICATION_JSON_VALUE)

                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.size()").isEqualTo(2);
    }

    @Test
    @DisplayName("create-validation error")
    void createValidationError() {
        String payload = """
                {
                    "title":" "
                }
                """;

        this.webTestClient.post()
                .uri(CategoryController.CATEGORY_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(422));
    }

    @Test
    @DisplayName("create-duplicate error")
    @SqlGroup(value = {@Sql(value = "classpath:scripts/INIT_Category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void createDuplicateError() {
        String payload = """
                {
                    "title":"indian"
                }
                """;

        this.webTestClient.post()
                .uri(CategoryController.CATEGORY_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(400));
    }


    @Test
    @DisplayName("create-success")
    @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createSuccess() {
        String payload1 = """
                {
                    "title":"south indian",
                    "description":"jjkh"
                }
                """;

        this.webTestClient.post()
                .uri(CategoryController.CATEGORY_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload1)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.title").isEqualTo("south indian");

        String payload2 = """
                {
                    "title":"desi",
                    "description":"indian dishes cook in old ways"
                }
                """;

        this.webTestClient.post()
                .uri(CategoryController.CATEGORY_PATH)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload2)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @SqlGroup(value = {@Sql(value = "classpath:scripts/INIT_Category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void update() {
        String payload = """
                {
                    "title":"%s"
                }
                """;
        this.webTestClient.put()
                .uri(CategoryController.CATEGORY_PATH + "/" + 1)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload.formatted("updated indian cuisine"))
                .exchange()
                .expectStatus().isOk();

        // should throw duplicate error -> uomId:1 already have categoryTitle of "updated indian cuisine"
        this.webTestClient.put()
                .uri(CategoryController.CATEGORY_PATH + "/" + 4)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload.formatted("updated indian cuisine"))
                .exchange()
                .expectStatus().is4xxClientError();

        this.webTestClient.put()
                .uri(CategoryController.CATEGORY_PATH + "/" + 4)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload.formatted("korean raw"))
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    @SqlGroup(value = {@Sql(value = "classpath:scripts/INIT_Category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:scripts/clean_db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void delete() {
        this.webTestClient.delete()
                .uri(CategoryController.CATEGORY_PATH + "/" + 4)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk();

        // should throw because categoryId:4 already deleted
        this.webTestClient.delete()
                .uri(CategoryController.CATEGORY_PATH + "/" + 4)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is4xxClientError();
    }
}