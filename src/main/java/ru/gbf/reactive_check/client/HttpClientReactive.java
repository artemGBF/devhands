package ru.gbf.reactive_check.client;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import reactor.core.publisher.Flux;

@Client
public interface HttpClientReactive {

    @Get("http://localhost:8082/slow")
    Flux<SlowData> slow();

    @Get("http://localhost:8082/fast")
    @SingleResult
    Flux<Long> fast();
}