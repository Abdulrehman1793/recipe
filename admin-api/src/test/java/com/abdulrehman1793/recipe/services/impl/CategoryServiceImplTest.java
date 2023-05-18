package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.Category;
import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.CategoryRepository;
import com.abdulrehman1793.recipe.web.mappers.CategoryMapper;
import com.abdulrehman1793.recipe.web.models.request.CategoryRequest;
import com.abdulrehman1793.recipe.web.models.response.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl cut;

    @Test
    @DisplayName("findById-should throw on invalid id")
    void shouldThrowOnInvalidIdOnFindById() {
        long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> cut.findById(categoryId));

        verify(categoryRepository).findById(categoryId);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("findById-should return category")
    void shouldReturnCategory() {
        long categoryId = 1L;

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(Category.builder().id(categoryId).build()));

        cut.findById(categoryId);

        verify(categoryRepository).findById(categoryId);
        verify(categoryMapper).categoryToCategoryResponse(any(Category.class));
    }

    @Test
    @DisplayName("findAll-should call findAll and return empty result")
    void shouldReturnEmptyResponse() {
        String keyword = "";
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<CategoryResponse> responses = cut.findAll(keyword);

        assertNotNull(responses);
        assertEquals(responses.size(), 0);

        verify(categoryRepository).findAll();
        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("findAll-should call findAll and return empty result")
    void shouldReturnResponse() {
        when(categoryRepository.findAll())
                .thenReturn(List.of(Category.builder().id(1L).build(), Category.builder().id(2L).build()));

        List<CategoryResponse> responses = cut.findAll(null);

        assertNotNull(responses);
        assertEquals(responses.size(), 2);

        verify(categoryRepository).findAll();
        verifyNoMoreInteractions(categoryRepository);
        verify(categoryMapper, times(2)).categoryToCategoryResponse(any(Category.class));
    }

    @Test
    @DisplayName("findAll-should search and return empty result")
    void shouldReturnEmptyResponseOnValidKeyword() {
        String keyword = "indian cuisine";

        when(categoryRepository.findByTitleContainsOrDescriptionContains(keyword, keyword))
                .thenReturn(List.of());

        List<CategoryResponse> responses = cut.findAll(keyword);

        assertNotNull(responses);
        assertEquals(responses.size(), 0);

        verify(categoryRepository).findByTitleContainsOrDescriptionContains(keyword, keyword);
        verify(categoryRepository, never()).findAll();
        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("findAll-should search and return result")
    void shouldReturnResponseOnValidKeyword() {
        String keyword = "indian cuisine";

        when(categoryRepository.findByTitleContainsOrDescriptionContains(keyword, keyword))
                .thenReturn(List.of(Category.builder().id(42L).build(), Category.builder().id(67L).build()));

        List<CategoryResponse> responses = cut.findAll(keyword);

        assertNotNull(responses);
        assertEquals(responses.size(), 2);

        verify(categoryRepository).findByTitleContainsOrDescriptionContains(keyword, keyword);
        verify(categoryRepository, never()).findAll();
        verifyNoMoreInteractions(categoryRepository);
        verify(categoryMapper, times(2)).categoryToCategoryResponse(any(Category.class));
    }

    @Test
    @DisplayName("create-should throw on category exists")
    void shouldThrowOnDuplicateCategory() {
        String categoryTitle = "indian cuisine";
        when(categoryRepository.existsByTitle(categoryTitle)).thenReturn(true);

        assertThrows(BadRequestException.class,
                () -> cut.create(CategoryRequest.builder().title(categoryTitle).build()));
    }

    @Test
    @DisplayName("create-should save and return response")
    void shouldSaveAndReturnResponse() {
        String categoryTitle = "indian cuisine";
        CategoryRequest categoryRequest = CategoryRequest.builder().title(categoryTitle).build();
        Category category = Category.builder().build();
        Category savedCategory = Category.builder().build();

        when(categoryRepository.existsByTitle(categoryTitle)).thenReturn(false);
        when(categoryMapper.categoryRequestToCategory(categoryRequest)).thenAnswer(invocation -> {
            CategoryRequest invocationArgument = invocation.getArgument(0, CategoryRequest.class);
            category.setTitle(invocationArgument.getTitle());
            return category;
        });
        when(categoryRepository.save(category)).thenAnswer(invocation -> {
            Category argument = invocation.getArgument(0, Category.class);
            savedCategory.setId(42L);
            savedCategory.setTitle(argument.getTitle());
            return savedCategory;
        });
        when(categoryMapper.categoryToCategoryResponse(savedCategory))
                .thenAnswer(invocation -> {
                    Category argument = invocation.getArgument(0, Category.class);
                    return CategoryResponse.builder()
                            .id(argument.getId())
                            .title(argument.getTitle())
                            .build();
                });

        CategoryResponse categoryResponse = cut.create(categoryRequest);

        assertNotNull(categoryResponse);
        assertNotNull(categoryResponse.getId());

        InOrder inOrder = inOrder(categoryRepository, categoryMapper);

        inOrder.verify(categoryRepository).existsByTitle(anyString());
        inOrder.verify(categoryMapper).categoryRequestToCategory(any(CategoryRequest.class));
        inOrder.verify(categoryRepository).save(any(Category.class));
        inOrder.verify(categoryMapper).categoryToCategoryResponse(any(Category.class));
        verifyNoMoreInteractions(categoryRepository);
        verifyNoMoreInteractions(categoryMapper);

    }

    @Test
    @DisplayName("update-should throw on invalid id")
    void updateThrowsOnInvalidId() {
        Long categoryId = 6L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class,
                () -> cut.update(categoryId, CategoryRequest.builder().build()));

        verify(categoryRepository).findById(categoryId);
        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("update-should throw on duplicate")
    void updateThrowsOnDuplicate() {
        Long categoryId = 6L;
        String categoryTitle = "indian";
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(Category.builder().id(categoryId).build()));

        when(categoryRepository.existsByTitleAndIdNot(categoryTitle,
                categoryId)).thenReturn(true);

        assertThrows(BadRequestException.class,
                () -> cut.update(categoryId,
                        CategoryRequest.builder().title(categoryTitle).build()));

        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).existsByTitleAndIdNot(anyString(), anyLong());
        verify(categoryRepository, never()).existsByTitle(anyString());
        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("update-should update and return response")
    void updateAndReturnResponse() {
        Long categoryId = 6L;
        String categoryTitle = "indian";

        Category category = Category.builder().id(categoryId).title("indian cuisine").build();
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .title(categoryTitle)
                .build();
        Category savedCategory = Category.builder().build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        when(categoryRepository.existsByTitleAndIdNot(categoryTitle, categoryId)).thenReturn(false);

        when(categoryRepository.save(category)).thenAnswer(invocation -> {
            Category argument = invocation.getArgument(0, Category.class);
            savedCategory.setId(argument.getId());
            savedCategory.setTitle(argument.getTitle());
            return savedCategory;
        });

        when(categoryMapper.categoryToCategoryResponse(savedCategory))
                .thenAnswer(invocation -> {
                    Category argument = invocation.getArgument(0, Category.class);
                    return CategoryResponse.builder().id(argument.getId()).title(argument.getTitle()).build();
                });

        CategoryResponse categoryResponse = cut.update(categoryId, categoryRequest);

        assertNotNull(categoryResponse);
        assertEquals(categoryResponse.getTitle(), categoryTitle);

        InOrder inOrder = inOrder(categoryRepository, categoryMapper);

        inOrder.verify(categoryRepository).findById(categoryId);
        inOrder.verify(categoryRepository).existsByTitleAndIdNot(anyString(), anyLong());
        inOrder.verify(categoryRepository).save(any(Category.class));
        inOrder.verify(categoryMapper).categoryToCategoryResponse(any(Category.class));
        verifyNoMoreInteractions(categoryRepository);
        verifyNoMoreInteractions(categoryMapper);
    }

    @Test
    @DisplayName("delete-should throw on invalid id")
    void deleteThrowOnInvalidId() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class,
                () -> cut.delete(categoryId));

        verify(categoryRepository).findById(categoryId);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("delete-should delete successfully")
    void deleteSuccess() {
        Long categoryId = 1L;
        Category category = Category.builder().id(categoryId).build();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        doNothing().when(categoryRepository).delete(category);

        cut.delete(categoryId);

        InOrder inOrder = inOrder(categoryRepository);

        inOrder.verify(categoryRepository).findById(categoryId);
        inOrder.verify(categoryRepository).delete(category);
    }
}