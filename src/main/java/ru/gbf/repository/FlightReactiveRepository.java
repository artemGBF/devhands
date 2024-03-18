package ru.gbf.repository;

import java.util.List;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import ru.gbf.model.Flight;

import reactor.core.publisher.Flux;

@R2dbcRepository(dialect = Dialect.POSTGRES, dataSource = "reactive")
public interface FlightReactiveRepository extends ReactiveStreamsCrudRepository<Flight, Long> {
    Flux<Flight> findByIdIn(List<Long> ids);

    @Query("select * from public.flight order by random() limit 10000")
    Flux<Flight> get10000();
}
