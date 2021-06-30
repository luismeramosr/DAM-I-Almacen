package com.idat.almacen.core.api.dto.responses;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResWrapper<T> {
    private T data;
    private Long timestamp;
    private String errorMessage;
}
