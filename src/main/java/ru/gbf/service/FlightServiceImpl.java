package ru.gbf.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.gbf.model.Flight;
import ru.gbf.model.Status;
import ru.gbf.repository.FlightRepository;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private static final String[] airports = {"KUL", "MOW", "MAL", "BER", "MOW", "MUN", "LA", "SA", "RIO", "SP", "NOV", "AA", "MOW", "MOW"};

    private final FlightRepository flightRepository;
    private final SecureRandom random = new SecureRandom();

    //@Cacheable(value = "flights")
    public List<Flight> getFlightsToDate1PK(LocalDate date) {
        return flightRepository.findByEstimatedTimeBetween(date.atStartOfDay(), date.atStartOfDay().plusDays(1));
    }

    public List<Flight> getFlightsToDate5PK(LocalDate date) {
        flightRepository.findByEstimatedTimeBetween(date.minusDays(10).atStartOfDay(), date.minusDays(10).atStartOfDay().plusDays(1));
        flightRepository.findByEstimatedTimeBetween(date.minusDays(8).atStartOfDay(), date.minusDays(8).atStartOfDay().plusDays(1));
        flightRepository.findByEstimatedTimeBetween(date.minusDays(6).atStartOfDay(), date.minusDays(6).atStartOfDay().plusDays(1));
        flightRepository.findByEstimatedTimeBetween(date.minusDays(4).atStartOfDay(), date.minusDays(4).atStartOfDay().plusDays(1));
        return flightRepository.findByEstimatedTimeBetween(date.atStartOfDay(), date.atStartOfDay().plusDays(1));
    }

    //@CacheInvalidate(value = "flights", all = true)
    public void generateToDate(LocalDate date) {
        log.info("CREATING FLIGHTS FOR TODAY...");
        int flightsCount = random.nextInt(100) + 100;
        List<Flight> flights = new ArrayList<>();
        for (int i = 0; i < flightsCount; i++) {
            String airport = airports[random.nextInt(14)];
            LocalDateTime estimatedTime = date.atStartOfDay().plusMinutes(random.nextLong(1200));
            Flight flight = new Flight(null, airport, getAirportTo(airport), estimatedTime, getStatus(estimatedTime));
            validateFlight(flight);
            flights.add(flight);
        }
        flightRepository.saveAll(flights);
    }

    //@CacheInvalidate(value = "flights", all = true)
    public void balanceFlights(LocalDate date) {
        List<Flight> todayFlights = flightRepository.findByEstimatedTimeBetween(date.atStartOfDay(), date.atStartOfDay().plusDays(1));
        if (todayFlights.isEmpty()) {
            generateToDate(date);
            todayFlights = flightRepository.findByEstimatedTimeBetween(date.atStartOfDay(), date.atStartOfDay().plusDays(1));
        }
        int countOfChanges = random.nextInt(10);
        for (int i = 0; i < countOfChanges; i++) {
            int index = random.nextInt(todayFlights.size());
            Flight flight = todayFlights.get(index);
            flight.setStatus(getStatus(flight.getEstimatedTime()));
        }
        flightRepository.updateAll(todayFlights);
    }

    private String getAirportTo(String airportFrom) {
        if (!airportFrom.equals("MOW")) {
            return "MOW";
        } else {
            String airport = airports[random.nextInt(14)];
            while (airport.equals("MOW")) {
                airport = airports[random.nextInt(14)];
            }
            return airport;
        }
    }

    private Status getStatus(LocalDateTime estimatedDate) {
        int i = random.nextInt(3);
        if (estimatedDate.isAfter(LocalDateTime.now())) {
            return Status.getForFutureDate()[i];
        }
        return Status.getForPastDate()[i];
    }

    private void validateFlight(Flight flight) {
        while (flight.getAirportTo().equals(flight.getAirportFrom())) {
            flight.setAirportFrom(airports[random.nextInt(14)]);
        }
    }
}
