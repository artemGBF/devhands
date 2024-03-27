package ru.gbf.service;

import java.time.LocalDate;
import java.util.List;

import ru.gbf.model.Flight;

public interface FlightService {

    List<Flight> getFlightsToDate1PK(LocalDate date);

    List<Flight> getFlightsToDate5PK(LocalDate date);

    void generateToDate(LocalDate date);

    void balanceFlights(LocalDate date);
}
