package ru.gbf.repository;

import java.time.LocalDateTime;
import java.util.List;

import ru.gbf.model.Flight;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface FlightRepository extends CrudRepository<Flight, Long> {
    List<Flight> findByIdIn(List<Long> ids);

    List<Flight> findByEstimatedTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query("select * from public.flight order by random() limit 10000")
    List<Flight> get10000();
}
