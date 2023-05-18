package com.abdulrehman1793.recipe.services.impl;

import com.abdulrehman1793.recipe.domains.UnitOfMeasure;
import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import com.abdulrehman1793.recipe.exceptions.NoSuchElementFoundException;
import com.abdulrehman1793.recipe.repositories.UnitOfMeasureRepository;
import com.abdulrehman1793.recipe.web.mappers.UnitOfMeasureMapper;
import com.abdulrehman1793.recipe.web.models.response.UnitOfMeasureResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository uomRepository;

    @Mock
    private UnitOfMeasureMapper unitOfMeasureMapper;

    @InjectMocks
    private UnitOfMeasureServiceImpl cut;

    Long uomId = 1L;
    String uomText = "teaspoon";
    UnitOfMeasure uom = UnitOfMeasure.builder().id(1L).uom("teaspoon").build();
    UnitOfMeasureResponse uomResponse = new UnitOfMeasureResponse(uom.getId(), uom.getUom());
    UnitOfMeasure pinchUOM = UnitOfMeasure.builder().id(2L).uom("pinch").build();

    @Test
    @DisplayName("findAll - should return only list of uom's contains search keyword")
    void shouldReturnListContainingUOM() {
        when(uomRepository.findAllByUomContaining(uomText)).thenReturn(List.of(uom));

        when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureResponse(uom)).thenReturn(uomResponse);

        List<UnitOfMeasureResponse> unitOfMeasureResponses = cut.findAll(uomText);

        assertNotNull(unitOfMeasureResponses);

        InOrder inOrder = inOrder(uomRepository, unitOfMeasureMapper);

        inOrder.verify(uomRepository, never()).findAll();
        inOrder.verify(uomRepository).findAllByUomContaining(any());
        inOrder.verify(unitOfMeasureMapper).unitOfMeasureToUnitOfMeasureResponse(any());
    }

    @Test
    @DisplayName("findAll - should return all list of uom")
    void shouldReturnAllUOMs() {
        String uomText = " ";

        List<UnitOfMeasure> unitOfMeasures = List.of(uom, pinchUOM);

        when(uomRepository.findAll()).thenReturn(unitOfMeasures);

        unitOfMeasures.forEach(unitOfMeasure -> when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureResponse(unitOfMeasure))
                .thenReturn(new UnitOfMeasureResponse(unitOfMeasure.getId(), unitOfMeasure.getUom())));


        List<UnitOfMeasureResponse> unitOfMeasureResponses = cut.findAll(uomText);

        assertNotNull(unitOfMeasureResponses);

        InOrder inOrder = inOrder(uomRepository, unitOfMeasureMapper);

        inOrder.verify(uomRepository).findAll();
        inOrder.verify(uomRepository, never()).findAllByUomContaining(any());
        inOrder.verify(unitOfMeasureMapper, times(2)).unitOfMeasureToUnitOfMeasureResponse(any());
    }

    @Test
    @DisplayName("findById - should return uom by id")
    void shouldReturnUomById() {
        when(uomRepository.findById(uomId)).thenReturn(Optional.of(uom));

        when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureResponse(uom)).thenReturn(uomResponse);

        UnitOfMeasureResponse unitOfMeasureResponse = cut.findById(uomId);

        assertThat(unitOfMeasureResponse).isNotNull();
        verify(uomRepository).findById(uomId);
    }

    @Test
    @DisplayName("findById - should throw exception")
    void shouldThrowOnFindById() {
        when(uomRepository.findById(uomId)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> cut.findById(uomId));

        verify(uomRepository, times(1)).findById(uomId);
    }

    @Test
    @DisplayName("create - should throw duplicate exception")
    void shouldThrowDuplicateException() {
        when(uomRepository.findByUom(uomText)).thenReturn(Optional.of(uom));

        assertThrows(RuntimeException.class, () -> cut.create(uomText));

        verify(uomRepository, times(1)).findByUom(uomText);
    }

    @Test
    @DisplayName("create-should save and return uom")
    void shouldSaveAndReturnSuccessfully() {
        when(uomRepository.findByUom(uomText)).thenReturn(Optional.empty());

        when(uomRepository.save(uom)).thenReturn(uom);

        when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureResponse(uom)).thenReturn(uomResponse);

        UnitOfMeasureResponse response = cut.create(uomText);

        assertThat(response).isNotNull();
        assertThat(response.getUom()).isEqualTo(uomText);

        InOrder inOrder = inOrder(uomRepository, unitOfMeasureMapper);

        inOrder.verify(uomRepository).findByUom(uomText);
        inOrder.verify(uomRepository).save(any());
        inOrder.verify(unitOfMeasureMapper).unitOfMeasureToUnitOfMeasureResponse(any());
    }

    @Test
    @DisplayName("update-should throw on invalid id")
    void shouldThrowOnUpdateUOM() {
        when(uomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> cut.update(1L, uomText));
    }

    @Test
    @DisplayName("update-should throw on no change of uom text")
    void shouldThrowOnNoChanges() {
        when(uomRepository.findById(1L)).thenReturn(Optional.of(uom));

        assertThrows(BadRequestException.class, () -> cut.update(1L, uomText));
    }

    @Test
    @DisplayName("update-should throw on duplicate uom text")
    void shouldThrowDuplicate() {
        when(uomRepository.findById(1L)).thenReturn(Optional.of(uom));

        when(uomRepository.findByUomAndIdNot("pinch", 1L)).thenReturn(Optional.of(pinchUOM));

        assertThrows(BadRequestException.class, () -> cut.update(1L, "pinch"));
    }

    @Test
    @DisplayName("update-should update successfully")
    void shouldUpdateUOM() {
        String uomTextToUpdate = "pinch";

        when(uomRepository.findById(1L)).thenReturn(Optional.of(uom));

        when(uomRepository.findByUomAndIdNot(uomTextToUpdate, 1L)).thenReturn(Optional.empty());

        UnitOfMeasure uomAfterUpdate = UnitOfMeasure.builder().id(1L).uom(uomTextToUpdate).build();
        when(uomRepository.save(uom)).thenReturn(uomAfterUpdate);

        UnitOfMeasureResponse response = new UnitOfMeasureResponse(1L, uomTextToUpdate);
        when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureResponse(uomAfterUpdate)).thenReturn(response);

        UnitOfMeasureResponse updatedUOM = cut.update(1L, uomTextToUpdate);

        assertNotNull(updatedUOM);
        assertEquals(updatedUOM.getId(), 1L);
        assertEquals(updatedUOM.getUom(), uomTextToUpdate);
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