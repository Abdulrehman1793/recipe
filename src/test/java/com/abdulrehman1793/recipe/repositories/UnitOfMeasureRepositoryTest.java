package com.abdulrehman1793.recipe.repositories;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UnitOfMeasureRepositoryTest {
    @Container
    static MySQLContainer<?> container = (MySQLContainer<?>) new MySQLContainer("mysql:8.0.32")
            .withDatabaseName("test")
            .withUsername("abdul")
            .withPassword("abdul")
            .withReuse(true);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UnitOfMeasureRepository cut;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {

    }

    @Test
    void notNull() {
        assertNotNull(entityManager);
        assertNotNull(dataSource);
        assertNotNull(cut);
        assertNotNull(testEntityManager);
    }

    @Test
    @Sql(scripts = "/scripts/INIT_UOM.sql")
    void findBy() {
        assertEquals(5, cut.count());
        assertEquals(2, cut.findAllByUomContaining("am").size());
        assertEquals(1, cut.findAllByUomContaining("oo").size());
        assertEquals(0, cut.findAllByUomContaining(" ").size());
        assertNotNull(cut.findByUom("pinch"));
        assertNotNull(cut.findByUomAndIdNot("pinch", 11L));
        assertFalse(cut.findByUomAndIdNot("pinch", 11L).isEmpty());
        assertTrue(cut.findByUomAndIdNot("teaspoon", 1L).isEmpty());
    }
}