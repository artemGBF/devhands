package com.example.dto;

import java.io.Serializable;

import com.example.model.Status;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
public class FlightDTO implements Serializable {
    private Long id;
    private String airportTo;
    private String airportFrom;
    private Status status;
}
