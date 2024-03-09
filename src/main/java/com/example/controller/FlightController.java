package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import com.example.dto.FlightDTO;
import com.example.mapper.FlightDTOMapper;
import com.example.service.FlightServiceImpl;
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

    @Get
    public List<FlightDTO> getFlightsToDate(@QueryValue LocalDate date, @QueryValue Boolean outgoing) {
        return flightDTOMapper.toFlightDTOs(flightService.getFlightsToDate(date, outgoing));
    }

    @Post
    public void generateToDate(@QueryValue LocalDate date) {
        flightService.generateToDate(date);
    }
}
