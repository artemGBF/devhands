package ru.gbf.dto;

import java.io.Serializable;

import ru.gbf.model.Status;
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
