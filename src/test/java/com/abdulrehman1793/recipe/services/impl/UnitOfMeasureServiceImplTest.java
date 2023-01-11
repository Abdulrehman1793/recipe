package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository uomRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private UnitOfMeasureServiceImpl cut;

    Long uomId = 1L;
    String uomText = "teaspoon";
    UnitOfMeasure uom = new UnitOfMeasure(uomId, uomText);

    Page<UnitOfMeasure> page = Page.empty(PageRequest.of(0, 1));

    @Test
    @DisplayName("findPage-should return uom page on passing valid parameter")
    void shouldReturnPage() {
        when(uomRepository.findAll(pageable)).thenReturn(page);

        Page<UnitOfMeasure> unitOfMeasurePage = cut.findPage(pageable);

        assertNotNull(unitOfMeasurePage);
        verify(uomRepository, times(1)).findAll(pageable);
        verify(uomRepository, never()).findAll();
    }

    @Test
    @DisplayName("findById-should return uom by id successfully")
    void shouldReturnUomById() {
        when(uomRepository.findById(uomId)).thenReturn(Optional.of(uom));

        UnitOfMeasure uom = cut.findById(uomId);

        assertThat(uom).isNotNull();
        verify(uomRepository, times(1)).findById(uomId);
    }

    @Test
    @DisplayName("findById-should throw exception on find by Id")
    void shouldThrowOnFindById() {
        when(uomRepository.findById(uomId)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> cut.findById(uomId));

        verify(uomRepository, times(1)).findById(uomId);
    }

    @Test
    @DisplayName("create-should throw duplicate exception")
    void shouldThrowDuplicateException() {
        when(uomRepository.findByUom(uomText)).thenReturn(Optional.of(uom));

        assertThrows(RuntimeException.class, () -> cut.create(uomText));

        verify(uomRepository, times(1)).findByUom(uomText);
    }

    @Test
    @DisplayName("create-should save and return uom")
    void shouldSaveAndReturnSuccessfully() {
        when(uomRepository.findByUom(uomText)).thenReturn(Optional.empty());

        when(uomRepository.save(new UnitOfMeasure(uomText))).thenReturn(uom);

        UnitOfMeasure createdUOM = cut.create(uom.getUom());

        assertThat(createdUOM).isNotNull();
        assertThat(createdUOM.getUom()).isEqualTo(uomText);

        verify(uomRepository, times(1)).findByUom(uomText);
        verify(uomRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("delete-should be able to delete uom")
    void shouldDeleteUomSuccessfully() {
        when(uomRepository.findById(uomId)).thenReturn(Optional.of(uom));

        doNothing().when(uomRepository).delete(uom);

        cut.delete(uomId);

        verify(uomRepository, times(1)).findById(uomId);
        verify(uomRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("delete-should throw exception uom not found")
    void shouldThrowOnUomDelete() {
        when(uomRepository.findById(anyLong())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> cut.delete(anyLong()));

        verify(uomRepository, times(1)).findById(anyLong());
        verify(uomRepository, never()).delete(any());
    }
}