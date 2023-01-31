package com.abdulrehman1793.recipe.bootstrap;

import com.abdulrehman1793.recipe.repositories.CategoryRepository;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.repositories.UnitOfMeasureRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
@RequiredArgsConstructor
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    private final ObjectMapper mapper;

    @SneakyThrows
    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    }

}
