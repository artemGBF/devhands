package com.example.model;

import java.time.LocalDateTime;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@MappedEntity("flight")
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(value = GeneratedValue.Type.IDENTITY)
    private Long id;
    private String airportTo;
    private String airportFrom;
    private LocalDateTime estimatedTime;
    private Status status;
}
