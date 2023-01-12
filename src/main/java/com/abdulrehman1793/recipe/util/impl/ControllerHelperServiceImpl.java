package com.abdulrehman1793.recipe.util.impl;

import com.abdulrehman1793.recipe.util.ControllerHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ControllerHelperServiceImpl<T> implements ControllerHelperService<T> {

    @Override
    public Sort sortRequestParameterToSort(String[] sortParameters) {
        if (sortParameters == null || sortParameters.length == 0) return Sort.unsorted();

        List<Sort.Order> orders = new ArrayList<>();
        for (String sortParameter : sortParameters) {
            String[] sortPair = sortParameter.split(":");
            orders.add(new Sort.Order(getSortDirection(sortPair[1]), sortPair[0]));
        }
        return Sort.by(orders);
    }

    @Override
    public Sort sortRequestParameterToSort(T model, String[] sortParameters) {
        if (sortParameters == null || sortParameters.length == 0) return Sort.unsorted();

        List<Sort.Order> orders = new ArrayList<>();
        for (String sortParameter : sortParameters) {
            String[] sortPair = sortParameter.split(":");
            if (!isFieldExists(model, sortPair[0])) {
                throw new RuntimeException("Invalid sort field " + sortPair[0]);
            }
            orders.add(new Sort.Order(getSortDirection(sortPair[1]), sortPair[0]));
        }

        return Sort.by(orders);
    }

    private boolean isFieldExists(T model, String fieldName) {
        return Arrays.stream(model.getClass().getFields())
                .anyMatch(f -> f.getName().equalsIgnoreCase(fieldName));
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equalsIgnoreCase("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
}
