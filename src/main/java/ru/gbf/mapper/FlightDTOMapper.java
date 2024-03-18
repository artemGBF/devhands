package ru.gbf.mapper;

import java.util.List;

import ru.gbf.dto.FlightDTO;
import ru.gbf.model.Flight;
import org.mapstruct.Mapper;

import reactor.core.publisher.Mono;

import static org.mapstruct.MappingConstants.ComponentModel.JSR330;

@Mapper(componentModel = JSR330)
public interface FlightDTOMapper {

    FlightDTO toFlightDTO(Flight flight);

    List<FlightDTO> toFlightDTOs(List<Flight> flights);
}
