package com.abdulrehman1793.recipe.util.impl;

import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import com.abdulrehman1793.recipe.util.ControllerHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ControllerHelperServiceImpl implements ControllerHelperService {

    @Override
    public Sort sortRequestParameterToSort(String[] domainFields, String[] sortParameters) {
        if (sortParameters == null || sortParameters.length == 0) return Sort.unsorted();

        List<Order> orders = new ArrayList<>();
        for (String sortParameter : sortParameters) {
            if (!sortParameter.contains(":"))
                throw new BadRequestException("Found invalid sort parameter: " + sortParameter);

            String[] sortPair = sortParameter.split(":");
            if (!contains(domainFields, sortPair[0])) {
                throw new BadRequestException("Found invalid sort field: " + sortPair[0]);
            }

            orders.add(new Order(getSortDirection(sortPair[1]), sortPair[0]));
        }

        return Sort.by(orders);
    }

    private boolean contains(String[] fields, String fieldName) {
        return Arrays.stream(fields).anyMatch(d -> d.equalsIgnoreCase(fieldName));
    }

    private Direction getSortDirection(String direction) {
        if (List.of("asc", "ascending").contains(direction.toLowerCase())) {
            return Direction.ASC;
        } else if (List.of("dsc", "desc", "descending").contains(direction.toLowerCase())) {
            return Direction.DESC;
        }
        throw new BadRequestException("Found invalid sort direction: " + direction);
    }
}
