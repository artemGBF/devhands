package ru.gbf.reactive_check.client;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Introspected
@Serdeable
public class SlowData {
    private Long id;
    private String value;
}
