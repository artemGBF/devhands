package ru.gbf.service;

import java.time.LocalDate;
import java.util.List;

import ru.gbf.model.Flight;

public interface FlightService {

    List<Flight> getFlightsToDate(LocalDate date, boolean outgoing);

    void generateToDate(LocalDate date);

    void balanceFlights(LocalDate date);
}
