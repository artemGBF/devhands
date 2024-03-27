package ru.gbf.controller;

import java.time.LocalDate;
import java.util.List;

import ru.gbf.dto.FlightDTO;
import ru.gbf.mapper.FlightDTOMapper;
import ru.gbf.service.FlightServiceImpl;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import lombok.RequiredArgsConstructor;

@Controller("/api/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightServiceImpl flightService;
    private final FlightDTOMapper flightDTOMapper;

    @Get("/1")
    public List<FlightDTO> getFlightsToDate1PK(@QueryValue LocalDate date) {
        return flightDTOMapper.toFlightDTOs(flightService.getFlightsToDate1PK(date));
    }

    @Get("/5")
    public List<FlightDTO> getFlightsToDate5PK(@QueryValue LocalDate date) {
        return flightDTOMapper.toFlightDTOs(flightService.getFlightsToDate5PK(date));
    }

    @Post
    public void generateToDate(@QueryValue LocalDate date) {
        flightService.generateToDate(date);
    }
}
