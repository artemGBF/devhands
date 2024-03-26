package ru.gbf.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;


@Data
@Serdeable
public class ChartDTO implements Serializable {
    List<Double> labels = new ArrayList<>();
    List<DatasetDTO> datasets = new ArrayList<>();

    public ChartDTO(String label) {
        this.datasets.add(new DatasetDTO(label));
    }

    public void add(double x, double y) {
        labels.add(x);
        datasets.get(0).getData().add(y);
    }
}

@Data
@Serdeable
class DatasetDTO implements Serializable {
    String label = "Latency";
    String backgroundColor = "blue";
    String borderColor = "blue";
    List<Double> data = new ArrayList<>();

    public DatasetDTO(String label) {
        this.label = label;
    }
}