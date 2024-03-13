package ru.gbf.reactive_check.client;

import java.util.List;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client
public interface HttpClient {

    @Get("http://localhost:8082/slow")
    List<SlowData> slow();

    @Get("http://localhost:8082/fast")
    Long fast();
}
