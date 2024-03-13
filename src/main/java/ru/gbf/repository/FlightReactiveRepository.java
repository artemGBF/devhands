package ru.gbf.repository;

import java.util.List;

import ru.gbf.model.Flight;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;

import reactor.core.publisher.Flux;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface FlightReactiveRepository extends ReactiveStreamsCrudRepository<Flight, Long> {
    Flux<Flight> findByIdIn(List<Long> ids);
}
