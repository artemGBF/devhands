package ru.gbf.service;

import java.time.LocalDate;

import io.micronaut.context.annotation.Requires;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@Requires(property = "devhands.scheduling.enabled", value = "true")
@RequiredArgsConstructor
public class FlightScheduler {
    private final FlightServiceImpl flightService;

    @Scheduled(fixedDelay = "2m", cron = "*/2 * * * *")
    public void balancing(){
        log.info("Balancing...");
        flightService.balanceFlights(LocalDate.now());
    }
}
