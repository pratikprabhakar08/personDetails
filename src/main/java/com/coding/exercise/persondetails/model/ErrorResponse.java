package com.coding.exercise.persondetails.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private Integer statusCode;

    private String message;

    private List<String> details;
}
