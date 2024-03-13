package com.example.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.model.Flight;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface FlightRepository extends CrudRepository<Flight, Long> {
    List<Flight> findByIdIn(List<Long> ids);

    List<Flight> findByEstimatedTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<Flight> findByEstimatedTimeBetweenAndAirportFrom(LocalDateTime startTime, LocalDateTime endTime, String airportFrom);

    List<Flight> findByEstimatedTimeBetweenAndAirportFromNotEqual(LocalDateTime startTime, LocalDateTime endTime, String airportFrom);
}
