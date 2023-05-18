package com.abdulrehman1793.recipe.util;

import org.springframework.data.domain.Sort;

public interface ControllerHelperService {
    Sort sortRequestParameterToSort(String[] domainFields, String[] sortParameters);
}
