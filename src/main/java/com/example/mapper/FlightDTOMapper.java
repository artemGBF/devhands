package com.example.mapper;

import java.util.List;

import com.example.dto.FlightDTO;
import com.example.model.Flight;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.JSR330;

@Mapper(componentModel = JSR330)
public interface FlightDTOMapper {

    FlightDTO toFlightDTO(Flight flight);

    List<FlightDTO> toFlightDTOs(List<Flight> flights);
}
