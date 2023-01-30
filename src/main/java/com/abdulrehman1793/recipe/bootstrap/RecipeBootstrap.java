package com.abdulrehman1793.recipe.bootstrap;

import com.abdulrehman1793.recipe.domains.*;
import com.abdulrehman1793.recipe.enums.Difficulty;
import com.abdulrehman1793.recipe.repositories.CategoryRepository;
import com.abdulrehman1793.recipe.repositories.RecipeRepository;
import com.abdulrehman1793.recipe.repositories.UnitOfMeasureRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ResourceUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        log.info("Bootstrapping data...");
//        recipeRepository.saveAll(getRecipes());
        log.debug("2 Recipe loaded successfully.");

        log.info("Loading data from json");
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Recipe> recipes = mapper.readValue(ResourceUtils.getFile("classpath:data/recipe.json"),
                new TypeReference<List<Recipe>>() {
                });

        for (Recipe recipe : recipes) {

            recipe.getCategories().forEach(category -> {
                Category categoryFromDB = categoryRepository.findByDescription(category.getDescription())
                        .orElseThrow(() -> new RuntimeException("expected category not found"));
//                category.setId(categoryFromDB.getId());
            });

            Set<Ingredient> ingredients = recipe.getIngredients();
            ingredients.forEach(ingredient -> {
                ingredient.setRecipe(recipe);
                UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findByUom(ingredient.getUnitOfMeasure().getUom())
                        .orElseThrow(() -> new RuntimeException("expected uom not found"));
                ingredient.setUnitOfMeasure(unitOfMeasure);
            });

            recipe.getPreparationSteps().forEach(preparationSteps -> {
                preparationSteps.setRecipe(recipe);
            });
        }

//        recipeRepository.saveAll(recipes);
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        /*Get unit of measures(Uom) */
        UnitOfMeasure teaSpoonUom = unitOfMeasureRepository.findByUom("teaspoon")
                .orElseThrow(() -> new RuntimeException("expected uom not found"));

        UnitOfMeasure tableSpoonUom = unitOfMeasureRepository.findByUom("tablespoon")
                .orElseThrow(() -> new RuntimeException("expected uom not found"));

        UnitOfMeasure cupUom = unitOfMeasureRepository.findByUom("cup")
                .orElseThrow(() -> new RuntimeException("expected uom not found"));

        UnitOfMeasure pinchUom = unitOfMeasureRepository.findByUom("pinch")
                .orElseThrow(() -> new RuntimeException("expected uom not found"));

        UnitOfMeasure ounceUom = unitOfMeasureRepository.findByUom("ounce")
                .orElseThrow(() -> new RuntimeException("expected uom not found"));

        UnitOfMeasure pintUom = unitOfMeasureRepository.findByUom("pint")
                .orElseThrow(() -> new RuntimeException("expected uom not found"));

        UnitOfMeasure numberUom = unitOfMeasureRepository.findByUom("number")
                .orElseThrow(() -> new RuntimeException("expected uom not found"));



        /* Get categories */

        Category americanCategory = categoryRepository.findByDescription("american")
                .orElseThrow(() -> new RuntimeException("expected category not found"));
        Category mexicanCategory = categoryRepository.findByDescription("mexican")
                .orElseThrow(() -> new RuntimeException("expected category not found"));
        Category indianCategory = categoryRepository.findByDescription("indian")
                .orElseThrow(() -> new RuntimeException("expected category not found"));
        Category italianCategory = categoryRepository.findByDescription("italian")
                .orElseThrow(() -> new RuntimeException("expected category not found"));
        Category chineseCategory = categoryRepository.findByDescription("chinese")
                .orElseThrow(() -> new RuntimeException("expected category not found"));

        /* Recipe #1: spicy grilled chicken taco */
        Recipe spicyGrilledChickenTaco = new Recipe();
        spicyGrilledChickenTaco.setDescription("Spicy grilled chicken tacos");
        spicyGrilledChickenTaco.setPrepTime(20);
        spicyGrilledChickenTaco.setCookTime(15);
        spicyGrilledChickenTaco.setServings(5);
        spicyGrilledChickenTaco.setSource("www.simplyrecipes.com");
        spicyGrilledChickenTaco.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        String spicyGrilledChickenTacoNote = """
                Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.
                First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.
                Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!
                The ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.
                I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.
                """;
        Note note = Note.builder().recipeNotes(spicyGrilledChickenTacoNote).build();
        spicyGrilledChickenTaco.setNote(note);
        spicyGrilledChickenTaco.setDifficulty(Difficulty.MEDIUM);
        spicyGrilledChickenTaco.getCategories().add(mexicanCategory);
        spicyGrilledChickenTaco.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoonUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), teaSpoonUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("sugar", new BigDecimal(1), teaSpoonUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teaSpoonUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("salt", new BigDecimal(1 / 2), teaSpoonUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("finely chopped garlic clove", new BigDecimal(1), numberUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), tableSpoonUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("fresh squeezed orange juice", new BigDecimal(3), tableSpoonUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("olive oil", new BigDecimal(2), tableSpoonUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("chicken thighs", new BigDecimal(5), numberUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("corn tortillas", new BigDecimal(8), numberUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("baby arugula", new BigDecimal(3), cupUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("sliced ripe avocados", new BigDecimal(2), numberUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("thinly sliced radishes", new BigDecimal(4), numberUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("halved cherry tomatoes", new BigDecimal("0.5"), pintUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("thinly sliced red onions", new BigDecimal("0.25"), numberUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("roughly chopped cilantro", new BigDecimal(1), pintUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("sour cream", new BigDecimal("0.5"), cupUom));
        spicyGrilledChickenTaco.addIngredient(new Ingredient("wedges cut lime", new BigDecimal(1), numberUom));


        /* Recipe #2:  Sautéed Zucchini with Dill */

        Recipe sautedZucchiniWithDill = new Recipe();
        sautedZucchiniWithDill.setDescription("Sautéed zucchini with dill");
        sautedZucchiniWithDill.setPrepTime(5);
        sautedZucchiniWithDill.setCookTime(25);
        sautedZucchiniWithDill.setServings(7);
        sautedZucchiniWithDill.setSource("www.simplyrecipes.com");
        sautedZucchiniWithDill.setUrl("http://www.simplyrecipes.com/recipes/sauteed_zucchini_with_dill/");
        String sautedZucchiniWithDillNoteText = """
                When the zucchini in your garden is still slender and about 7-inches long – before they get big as baseball bats — the seeds will be small and the flesh will be sweet.
                Zucchini at this stage hardly needs any adornment. I like to cut my zucchini into thin coins and toss them in a hot skillet with some oil until they start to turn golden brown. That’s it!This simple sauté takes just a few minutes. The zucchini can go to the table on its own, or you can use it as a bed for grilled chicken or fish. Any leftover zucchini is great served with a fried egg for breakfast the next day!
                I like to use a mandoline to slice the zucchini into thin, uniform circles, but you can also use a food processor with a slicing blade. If the opening of your food processor is too small for whole zucchini, halve them lengthwise to make half-coins.
                I like to add fresh dill and lemon zest to my sauté. They add just the right aromatics to the zucchini.
                With only six ingredients and about 15 minutes of your time, you have a beautiful vegetable dish for your summer table.
                """;
        Note sautedZucchiniWithDillNote = Note.builder().recipeNotes(sautedZucchiniWithDillNoteText).build();
        sautedZucchiniWithDill.setNote(sautedZucchiniWithDillNote);
        sautedZucchiniWithDill.setDifficulty(Difficulty.EASY);
        sautedZucchiniWithDill.getCategories().add(americanCategory);
        sautedZucchiniWithDill.addIngredient(new Ingredient("medium zucchini", new BigDecimal(6), numberUom));
        sautedZucchiniWithDill.addIngredient(new Ingredient("olive oil", new BigDecimal(4), tableSpoonUom));
        sautedZucchiniWithDill.addIngredient(new Ingredient("salt", new BigDecimal("0.5"), teaSpoonUom));
        sautedZucchiniWithDill.addIngredient(new Ingredient("ground black pepper", new BigDecimal("0.25"), teaSpoonUom));
        sautedZucchiniWithDill.addIngredient(new Ingredient("chopped fresh dill", new BigDecimal(2), tableSpoonUom));
        sautedZucchiniWithDill.addIngredient(new Ingredient("finely chopped garlic clove", new BigDecimal(1), numberUom));


        recipes.add(spicyGrilledChickenTaco);
        recipes.add(sautedZucchiniWithDill);

        return recipes;
    }


}
