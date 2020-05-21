package com.tsel.app.conventor;

import java.util.List;
import java.util.stream.Collectors;

public interface Convector<V, T> {

    T convertToDto(V entity);

    default List<T> convertListToDtos (List<V> entityList) {
        return entityList.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
}
