package com.abdulrehman1793.recipe.util;

import org.springframework.data.domain.Sort;

public interface ControllerHelperService<T> {
    Sort sortRequestParameterToSort(String[] sortParameters);

    Sort sortRequestParameterToSort(T model, String[] sortParameters);
}
