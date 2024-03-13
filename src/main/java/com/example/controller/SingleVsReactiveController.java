package com.example.controller;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.model.Flight;
import com.example.model.Status;
import com.example.reactive_check.client.HttpClient;
import com.example.reactive_check.client.HttpClientReactive;
import com.example.reactive_check.client.SlowData;
import com.example.repository.FlightReactiveRepository;
import com.example.repository.FlightRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.RequiredArgsConstructor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/react")
@RequiredArgsConstructor
public class SingleVsReactiveController {
    private final FlightRepository flightRepository;
    private final HttpClient httpClient;
    private final HttpClientReactive httpClientReactive;
    private final FlightReactiveRepository flightReactiveRepository;

    @Get("/single")
    @ExecuteOn(TaskExecutors.BLOCKING)
    public Long calculate() {
        List<SlowData> slow = httpClient.slow();
        System.out.println(slow);

        List<Flight> entities = flightRepository.findByIdIn(slow.stream().map(SlowData::getId).collect(Collectors.toList()));

        return httpClient.fast();
    }

    @Get("/reactive")
    public Mono<Object> calculateReactive() {
        return Mono.just(100)
            .flatMap(m -> {
                Flux<SlowData> slow = httpClientReactive.slow();
                return slow.collectList();
            })
            .doOnError(throwable -> System.out.println("There was an error occurred"))
            .doOnSuccess(System.out::println)
            .map(slowData -> slowData.stream().map(SlowData::getId).collect(Collectors.toList()))
            .map(longs -> flightReactiveRepository.findByIdIn(longs).map(Flight::getStatus))
            .flatMap(stringFlux -> httpClientReactive.fast().single());
    }
}
