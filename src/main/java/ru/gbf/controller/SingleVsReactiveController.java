package ru.gbf.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.micronaut.core.annotation.NonBlocking;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.reactor.http.client.ReactorHttpClient;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.RequiredArgsConstructor;
import ru.gbf.dto.FlightDTO;
import ru.gbf.mapper.FlightDTOMapper;
import ru.gbf.model.Flight;
import ru.gbf.reactive_check.client.HttpClient;
import ru.gbf.reactive_check.client.HttpClientReactive;
import ru.gbf.reactive_check.client.SlowData;
import ru.gbf.repository.FlightReactiveRepository;
import ru.gbf.repository.FlightRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/react")
@RequiredArgsConstructor
public class SingleVsReactiveController {
    private final FlightRepository flightRepository;
    private final HttpClient httpClient;
    private final HttpClientReactive httpClientReactive;
    private final FlightReactiveRepository flightReactiveRepository;
    private final ReactorHttpClient reactorHttpClient;
    private final FlightDTOMapper flightDTOMapper;

    @Get("/single")
    @ExecuteOn(TaskExecutors.BLOCKING)
    public Long calculate() {
        System.out.println(Thread.currentThread().getName() + Thread.currentThread().getState());
        List<SlowData> slow = httpClient.slow();
        System.out.println(Thread.currentThread().getName() + Thread.currentThread().getState());

        List<Flight> entities = flightRepository.findByIdIn(slow.stream().map(SlowData::getId).collect(Collectors.toList()));
        System.out.println(Thread.currentThread().getName() + Thread.currentThread().getState());

        return httpClient.fast();
    }

    @Get("/reactive")
    public Mono<Long> calculateReactive() {
        return httpClientReactive.slow()
            .doOnSubscribe(s -> System.out.println(Thread.currentThread().getName()))
            .map(SlowData::getId)
            .flatMap(flightReactiveRepository::findById)
            .map(Flight::getStatus)
            .then(httpClientReactive.fast().single());
    }

    @Get("/getAll")
    public List<FlightDTO> getAll() {
        return flightRepository.get10000().stream()
            .map(flightDTOMapper::toFlightDTO)
            .collect(Collectors.toList());
    }

    @Get("/getAllR")
    public Flux<FlightDTO> getAllReactive() {
        return flightReactiveRepository.get10000()
            .flatMap(flight -> Mono.just(flightDTOMapper.toFlightDTO(flight)));
    }


}
